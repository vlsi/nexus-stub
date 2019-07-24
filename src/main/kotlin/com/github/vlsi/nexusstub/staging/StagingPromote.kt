package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "stagingPromote")
class StagingPromoteDto(
    @JacksonXmlProperty(localName = "data")
    val data: StagingPromote
) {
    override fun toString(): String {
        return "StagingPromoteDto(data=$data)"
    }
}

@JacksonXmlRootElement(localName = "stagingPromote")
data class StagingPromote(
    val stagedRepositoryId: String? = "",
    val description: String? = "",
    val targetRepositoryId: String? = ""
)
