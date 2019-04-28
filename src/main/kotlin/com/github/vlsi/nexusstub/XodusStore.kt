package com.github.vlsi.nexusstub

import com.github.vlsi.nexusstub.staging.store.XdRepositoryState
import com.github.vlsi.nexusstub.staging.store.XdStagingProfile
import com.github.vlsi.nexusstub.staging.store.XdStagingRepository

import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.XdModel
import kotlinx.dnq.store.container.StaticStoreContainer
import kotlinx.dnq.util.initMetaData
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
class XodusStore {
    @Bean
    fun xodus(@Value("\${nexusstub.xodus.path:build/nexusstub-xodus-db}") path: String): TransientEntityStore {
        XdModel.registerNodes(XdStagingProfile, XdStagingRepository, XdRepositoryState)
        val xodusStore = StaticStoreContainer.init(
            dbFolder = File(path),
            environmentName = "db"
        )
        initMetaData(XdModel.hierarchy, xodusStore)
        return xodusStore
    }
}