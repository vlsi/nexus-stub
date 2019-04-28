package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.store.XdRepositoryState
import com.github.vlsi.nexusstub.staging.store.XdStagingProfile
import com.github.vlsi.nexusstub.staging.store.XdStagingRepository
import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.query.filter
import kotlinx.dnq.query.firstOrNull
import org.springframework.stereotype.Component

@Component
class StagingRepositoryService(
    private val xodus: TransientEntityStore
) {
    fun findById(id: String) =
        XdStagingRepository.filter { it.id eq id }.firstOrNull()

    fun create(profile: XdStagingProfile) =
        XdStagingRepository.new {
            val repoName =
                profile.name.replace(Regex("\\."), "") + "-" + System.currentTimeMillis()
            this.id = repoName
            this.profile = profile
            this.type = XdRepositoryState.OPEN
        }
}
