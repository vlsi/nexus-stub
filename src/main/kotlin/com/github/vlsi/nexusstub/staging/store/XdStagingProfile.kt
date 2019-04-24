package com.github.vlsi.nexusstub.staging.store

import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.XdEntity
import kotlinx.dnq.XdNaturalEntityType
import kotlinx.dnq.xdRequiredStringProp
import kotlinx.dnq.xdStringProp

class XdStagingProfile(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdStagingProfile>()

    var id by xdRequiredStringProp()
    var name by xdStringProp()
}