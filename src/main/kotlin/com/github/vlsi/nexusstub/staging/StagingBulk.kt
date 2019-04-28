package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "stagingBulk")
class StagingBulkDto(
    @JacksonXmlProperty(localName = "data")
    val data: StagingBulk
)

@JacksonXmlRootElement(localName = "stagingBulk")
class StagingBulk(
    val stagedRepositoryIds: Array<String> = arrayOf(),
    val description: String? = ""
)
