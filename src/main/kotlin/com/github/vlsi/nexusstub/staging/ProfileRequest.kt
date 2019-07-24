package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "profileRequest")
class ProfileRequest(
    @JacksonXmlProperty(localName = "data")
    val data: StagingProfile
) {
    override fun toString(): String {
        return "ProfileRequest(data=$data)"
    }
}
