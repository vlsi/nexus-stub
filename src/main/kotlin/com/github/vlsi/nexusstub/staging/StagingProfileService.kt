package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.store.XdStagingProfile
import com.github.vlsi.nexusstub.staging.store.XdStagingRepository
import com.github.vlsi.nexusstub.staging.store.toDto
import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.creator.findOrNew
import kotlinx.dnq.query.filter
import kotlinx.dnq.query.firstOrNull
import kotlinx.dnq.query.toList
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class StagingProfileService(
    private val xodus: TransientEntityStore,
    private val repositoryService: StagingRepositoryService,
    @Value("\${nexusstub.contextpath}") private val contextPath: String
) {
    fun findAll(): List<StagingProfile> =
        XdStagingProfile.all().toList().asSequence()
            .map {
                it.toDto()
            }
            .toList()

    fun findById(id: String) =
        XdStagingProfile.filter { it.id eq id }.firstOrNull()

    fun deleteById(id: String) =
        XdStagingProfile.filter { it.id eq id }.firstOrNull()?.delete()

    fun putById(id: String, profile: StagingProfile) =
        XdStagingProfile.findOrNew { this.id = id }.apply {
            name = profile.name
        }

    fun start(profileId: String, request: StagingPromoteDto): XdStagingRepository {
        val profile = findById(profileId)
        return repositoryService.create(profile!!)
    }
}
