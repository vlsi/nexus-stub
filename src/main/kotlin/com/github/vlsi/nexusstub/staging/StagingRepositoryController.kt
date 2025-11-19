package com.github.vlsi.nexusstub.staging

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    "service/local/staging/repository",
    produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
)
class StagingRepositoryController(
    private val repositoryService: StagingRepositoryService
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<StagingProfileRepository> {
        val repository = repositoryService.getById(id)
        return repository?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
}
