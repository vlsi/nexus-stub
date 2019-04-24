package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.store.XdStagingProfile
import io.micronaut.context.annotation.Value
import io.micronaut.http.server.HttpServerConfiguration
import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.creator.findOrNew
import kotlinx.dnq.query.asSequence
import kotlinx.dnq.query.filter
import kotlinx.dnq.query.firstOrNull
import java.net.URI
import javax.inject.Singleton

@Singleton
class StagingProfileRepository(
    private val xodus: TransientEntityStore,
    private val httpServer: HttpServerConfiguration,
    @Value("\${nexusstub.contextpath}") private val contextPath: String
) {
    private val hostPort =
        httpServer.run { host.orElse("127.0.0.1") + ":" + port.orElse(HttpServerConfiguration.DEFAULT_PORT) }

    fun XdStagingProfile.toDto() =
        StagingProfile(
            id = id,
            name = name,
            resourceURI = URI.create("http://$hostPort/${contextPath}service/local/staging/profiles/$id")
        )

    fun findAll(): List<StagingProfile> =
        xodus.transactional(readonly = true) {
            XdStagingProfile.all().asSequence()
                .map {
                    it.toDto()
                }
                .toList()
        }

    fun findById(id: String) =
        xodus.transactional(readonly = true) {
            XdStagingProfile.filter { it.id eq id }.firstOrNull()?.toDto()
        }

    fun deleteById(id: String) =
        xodus.transactional(readonly = true) {
            XdStagingProfile.filter { it.id eq id }.firstOrNull()?.delete()
        }

    fun putById(id: String, profile: StagingProfile) =
        xodus.transactional {
            XdStagingProfile.findOrNew { this.id = id }.apply {
                name = profile.name
            }.toDto()
        }
}