package com.github.vlsi.nexusstub.staging

import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    "service/local/staging/bulk",
    produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
)
class StagingBulkController(
    private val repositoryService: StagingRepositoryService
) {
    @PostMapping("drop")
    fun drop(@Valid @RequestBody request: StagingBulkDto) {
        repositoryService.dropRepositories(request.data.stagedRepositoryIds)
    }

    @PostMapping("close")
    fun close(@Valid @RequestBody request: StagingBulkDto) {
        repositoryService.closeRepositories(request.data.stagedRepositoryIds)
    }

    @PostMapping("promote")
    fun promote(@Valid @RequestBody request: StagingBulkDto) {
        repositoryService.promoteRepositories(request.data.stagedRepositoryIds)
    }
}
