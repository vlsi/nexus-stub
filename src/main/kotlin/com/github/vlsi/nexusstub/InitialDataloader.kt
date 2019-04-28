package com.github.vlsi.nexusstub

import com.github.vlsi.nexusstub.staging.StagingProfile
import com.github.vlsi.nexusstub.staging.StagingProfileService
import jetbrains.exodus.database.TransientEntityStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InitialDataloader(
    private val xodus: TransientEntityStore,
    private val profiles: StagingProfileService,
    @Value("\${GROUP_IDS:}") private val groupIds: String
) {
    @PostConstruct
    fun populateData() {
        if (groupIds.isEmpty()) {
            return
        }
        xodus.transactional {
            for ((index, group) in groupIds.split(',').withIndex()) {
                val v = group.split(':')
                val profileId = if (v.size > 1) v[1] else "profile$index"
                profiles.put(
                    StagingProfile(id = profileId, name = v[0])
                )
            }
        }
    }
}
