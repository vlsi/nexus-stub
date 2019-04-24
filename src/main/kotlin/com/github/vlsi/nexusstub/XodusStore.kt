package com.github.vlsi.nexusstub

import com.github.vlsi.nexusstub.staging.store.XdStagingProfile
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.XdModel
import kotlinx.dnq.store.container.StaticStoreContainer
import kotlinx.dnq.util.initMetaData
import java.io.File
import javax.inject.Singleton

@Factory
class XodusStore {
    @Bean
    @Singleton
    fun xodus(@Value("\${nexusstub.xodus.path:build/nexusstub-xodus-db}") path: String): TransientEntityStore {
        XdModel.registerNodes(XdStagingProfile)
        val xodusStore = StaticStoreContainer.init(
            dbFolder = File(path),
            environmentName = "db"
        )
        initMetaData(XdModel.hierarchy, xodusStore)
        return xodusStore
    }
}