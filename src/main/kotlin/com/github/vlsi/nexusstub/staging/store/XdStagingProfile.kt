package com.github.vlsi.nexusstub.staging.store

import com.github.vlsi.nexusstub.staging.StagingProfile
import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.XdEntity
import kotlinx.dnq.XdNaturalEntityType
import kotlinx.dnq.xdRequiredStringProp

class XdStagingProfile(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdStagingProfile>()

    var id by xdRequiredStringProp(unique = true)
    var name by xdRequiredStringProp(unique = true)
}

fun XdStagingProfile.toDto() =
    StagingProfile(
        id = id,
        name = name
    )
