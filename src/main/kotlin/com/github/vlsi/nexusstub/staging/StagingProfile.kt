package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import java.net.URI


@JacksonXmlRootElement(localName = "stagingProfile")
data class StagingProfile(
    var resourceURI: URI? = null,
    var id: String?, // It is optional for "create" requests
    var name: String,
    var repositoryTemplateId: String = "default_hosted_release",
    var repositoryType: String = "maven2",
    var repositoryTargetId: String = "",
    var inProgress: Boolean = false,
    var order: Int = 42,
    var deployURI: URI? = null,
    var targetGroups: List<String> = mutableListOf(),
    var finishNotifyCreator: Boolean = true,
    var promotionNotifyCreator: Boolean = true,
    var dropNotifyCreator: Boolean = true,
    var autoStagingDisabled: Boolean = false,
    var repositoriesSearchable: Boolean = true
)
