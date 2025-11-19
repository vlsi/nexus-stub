package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

@JacksonXmlRootElement(localName = "stagingBulk")
class StagingBulkDto(
    @field:Valid
    @JacksonXmlProperty(localName = "data")
    val data: StagingBulk
) {
    override fun toString(): String {
        return "StagingBulkDto(data=$data)"
    }
}

@JacksonXmlRootElement(localName = "stagingBulk")
data class StagingBulk(
    @field:NotEmpty(message = "stagedRepositoryIds must not be empty")
    val stagedRepositoryIds: Array<String> = arrayOf(),
    val description: String? = ""
)
