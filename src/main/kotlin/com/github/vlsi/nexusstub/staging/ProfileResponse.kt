package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "profileResponse")
class ProfileResponse(
    @JacksonXmlProperty(localName = "data")
    val data: StagingProfile?
)