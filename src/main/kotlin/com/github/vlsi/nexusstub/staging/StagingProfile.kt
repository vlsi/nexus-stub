package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import java.net.URI


@JacksonXmlRootElement(localName = "stagingProfile")
data class StagingProfile(
    val resourceURI: URI? = null,
    val id: String?, // It is optional for "create" requests
    val name: String,
    val repositoryTemplateId: String = "default_hosted_release",
    val repositoryType: String = "maven2",
    val repositoryTargetId: String = "",
    val inProgress: Boolean = false,
    val order: Int = 42,
    val deployURI: URI? = null,
    val targetGroups: List<String> = mutableListOf(),
    val finishNotifyCreator: Boolean = true,
    val promotionNotifyCreator: Boolean = true,
    val dropNotifyCreator: Boolean = true,
    val autoStagingDisabled: Boolean = false,
    val repositoriesSearchable: Boolean = true
)
