package com.github.vlsi.nexusstub.staging.dto

import java.time.ZonedDateTime

data class ArtifactDto (
    val groupId: String,
    val artifactId: String,
    val classifier: String?,
    val extension: String?,
    val version: String,
    val updatedWhen: ZonedDateTime
)
