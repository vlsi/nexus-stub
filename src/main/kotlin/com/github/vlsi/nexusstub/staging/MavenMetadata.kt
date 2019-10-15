package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "metadata")
data class MavenMetadata (
    val groupId: String,
    val artifactId: String,
    val versioning: MavenMetadataVersioning?
)

@JsonPropertyOrder("latest", "release", "versions", "lastUpdated")
data class MavenMetadataVersioning(
    val latest: String,
    val release: String,
    @get:JacksonXmlElementWrapper(localName = "versions")
    @get:JacksonXmlProperty(localName = "version")
    val versions: List<String>,
    val lastUpdated: String
)
