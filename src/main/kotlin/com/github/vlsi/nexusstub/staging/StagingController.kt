package com.github.vlsi.nexusstub.staging

import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.HandlerMapping
import java.lang.IllegalArgumentException
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("service/local/staging")
class StagingController(
    private val profiles: StagingProfileService,
    private val artifactService: ArtifactService,
    private val repositoryService: StagingRepositoryService
) {
    @GetMapping(
        "deployByRepositoryId/{repositoryId}/**/{artifactId}/maven-metadata.xml",
        produces = [MediaType.APPLICATION_XML_VALUE]
    )
    @Transactional
    fun metadata(
        @PathVariable repositoryId: String,
        @PathVariable artifactId: String,
        request: HttpServletRequest
    ): MavenMetadata {
        val path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE) as String

        val startIndex =
            "/service/local/staging/deployByRepositoryId/".length + repositoryId.length + 1
        val endIndex = path.length - "/maven-metadata.xml".length
        val groupId = path.substring(startIndex, endIndex).replace('/', '.')

        val repository = repositoryService.findById(repositoryId) ?: throw IllegalArgumentException("Repository $repositoryId does not exist")
        val artifacts = artifactService.findByGroupAndArtifact(repository, groupId, artifactId)

        val dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val versioning = if (artifacts.isEmpty()) {
            null
        } else {
            // TODO: implement Maven comparison rules
            val versions = artifacts.map { it.version }
            val latest = versions.max()!!
            MavenMetadataVersioning(
                latest = latest,
                release = latest,
                versions = versions,
                lastUpdated = artifacts.maxBy { it.updatedWhen }!!.updatedWhen.format(dateFormat)
            )
        }
        return MavenMetadata(
            groupId = groupId,
            artifactId = artifactId,
            versioning = versioning
        )
    }

    @PutMapping("deployByRepositoryId/{repositoryId}/**")
    @Transactional
    fun deployByRepositoryId(
        @PathVariable repositoryId: String, @RequestBody contents: ByteArray,
        request: HttpServletRequest
    ) {
        println("Received ${contents.size} bytes for ${request.requestURL}")
    }
}
