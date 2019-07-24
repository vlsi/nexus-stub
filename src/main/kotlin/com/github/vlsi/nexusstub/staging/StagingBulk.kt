package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "stagingBulk")
class StagingBulkDto(
    @JacksonXmlProperty(localName = "data")
    val data: StagingBulk
) {
    override fun toString(): String {
        return "StagingBulkDto(data=$data)"
    }
}

@JacksonXmlRootElement(localName = "stagingBulk")
data class StagingBulk(
    val stagedRepositoryIds: Array<String> = arrayOf(),
    val description: String? = ""
)
