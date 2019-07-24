package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement


/**
 * <stagingProfileRepository>
<profileId>83a19ef842a1</profileId>
<profileName>com.github.vlsi</profileName>
<profileType>repository</profileType>
<repositoryId>comgithubvlsi-1055</repositoryId>
<type>open</type>
<policy>release</policy>
<userId>vladimirsitnikov</userId>
<userAgent>okhttp/3.12.0</userAgent>
<ipAddress>89.254.248.222</ipAddress>
<repositoryURI>
https://oss.sonatype.org/content/repositories/comgithubvlsi-1055
</repositoryURI>
<created>2019-04-21T18:50:51.953Z</created>
<createdDate>Sun Apr 21 18:50:51 UTC 2019</createdDate>
<createdTimestamp>1555872651953</createdTimestamp>
<updated>2019-04-21T18:50:56.822Z</updated>
<updatedDate>Sun Apr 21 18:50:56 UTC 2019</updatedDate>
<updatedTimestamp>1555872656822</updatedTimestamp>
<description>Explicitly created.</description>
<provider>maven2</provider>
<releaseRepositoryId>releases</releaseRepositoryId>
<releaseRepositoryName>Releases</releaseRepositoryName>
<notifications>0</notifications>
<transitioning>false</transitioning>
</stagingProfileRepository>
 */
@JacksonXmlRootElement(localName = "stagingProfileRepository")
data class StagingProfileRepository(
    val profileId: String,
    val profileName: String,
    val repositoryId: String,
    val type: String,
    val transitioning: Boolean = false
)
