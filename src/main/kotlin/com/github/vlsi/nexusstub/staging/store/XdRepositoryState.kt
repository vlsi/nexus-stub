package com.github.vlsi.nexusstub.staging.store

import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.XdEnumEntity
import kotlinx.dnq.enum.XdEnumEntityType
import kotlinx.dnq.xdRequiredStringProp

class XdRepositoryState(entity: Entity) : XdEnumEntity(entity) {
    companion object : XdEnumEntityType<XdRepositoryState>() {
        val OPEN by enumField { presentation = "open" }
        val CLOSED by enumField { presentation = "closed" }
    }

    var presentation by xdRequiredStringProp()
        private set
}
