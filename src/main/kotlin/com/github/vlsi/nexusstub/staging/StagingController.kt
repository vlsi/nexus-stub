package com.github.vlsi.nexusstub.staging

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("service/local/staging")
class StagingController(
    private val artifactService: ArtifactService
) {
    @GetMapping(
        "deployByRepositoryId/{repositoryId}/{*path}",
        produces = [MediaType.APPLICATION_XML_VALUE]
    )
    fun metadata(
        @PathVariable repositoryId: String,
        @PathVariable path: String
    ): MavenMetadata = artifactService.getMetadata(repositoryId, path)

    @PutMapping("deployByRepositoryId/{repositoryId}/{*path}")
    fun deployByRepositoryId(
        @PathVariable repositoryId: String,
        @PathVariable path: String,
        @RequestBody contents: ByteArray,
        request: HttpServletRequest
    ) {
        artifactService.deployArtifact(repositoryId, path, contents, request.requestURL.toString())
    }
}
