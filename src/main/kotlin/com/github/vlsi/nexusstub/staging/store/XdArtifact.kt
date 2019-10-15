package com.github.vlsi.nexusstub.staging.store

import com.github.vlsi.nexusstub.staging.dto.ArtifactDto
import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.*
import kotlinx.dnq.link.OnDeletePolicy
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

class XdArtifact(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdArtifact>()

    var groupId by xdRequiredStringProp()
    var artifactId by xdRequiredStringProp()
    var classifier by xdStringProp()
    var extension by xdStringProp()
    var version by xdRequiredStringProp()
    var updatedWhen by xdRequiredDateTimeProp()
    val repository by xdLink1(XdStagingRepository, onTargetDelete = OnDeletePolicy.CASCADE)
}

fun XdArtifact.toDto() =
    ArtifactDto(
        groupId = groupId,
        artifactId = artifactId,
        classifier = classifier,
        extension = extension,
        version = version,
        updatedWhen = ZonedDateTime.ofInstant(Instant.ofEpochMilli(updatedWhen.millis), ZoneOffset.UTC)
    )
