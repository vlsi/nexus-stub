package com.github.vlsi.nexusstub.staging

import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.HandlerMapping
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("service/local/staging")
class StagingController(
    private val profiles: StagingProfileService
) {
    @GetMapping(
        "deployByRepositoryId/{repositoryId}/**/maven-metadata.xml",
        produces = [MediaType.APPLICATION_XML_VALUE]
    )
    @Transactional
    fun metadata(
        @PathVariable repositoryId: String,
        request: HttpServletRequest
    ) {
        val path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)
        println("GET metadata: $path")
        // Actual response is not implemented yet, however Maven seems to be happy with just
        // receiving 200 OK.
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
