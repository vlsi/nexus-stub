package com.github.vlsi.nexusstub.staging.store

import com.github.vlsi.nexusstub.staging.StagingProfileRepository
import com.github.vlsi.nexusstub.staging.StagingPromote
import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.*
import kotlinx.dnq.link.OnDeletePolicy

class XdStagingRepository(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdStagingRepository>()

    var id by xdRequiredStringProp(unique = true)
    var description by xdStringProp()
    var profile by xdLink1(XdStagingProfile, onTargetDelete = OnDeletePolicy.CASCADE)
    var type by xdLink1(XdRepositoryState)
}

fun XdStagingRepository.toStagingPromote() =
    StagingPromote(
        stagedRepositoryId = id,
        description = description
    )

fun XdStagingRepository.toDto() =
    StagingProfileRepository(
        profileId = profile.id,
        profileName = profile.name,
        repositoryId = id,
        type = type.presentation
    )
