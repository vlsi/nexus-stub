package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "stagingProfiles")
class StagingProfiles {
    @JacksonXmlElementWrapper(localName = "data")
    @JacksonXmlProperty(localName = "stagingProfile")
    val data = mutableListOf<StagingProfile>()
}
