package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.store.toDto
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    "service/local/staging/repository",
    produces = [MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE]
)
class StagingRepositoryController(
    private val repositoryService: StagingRepositoryService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun get(@PathVariable id: String) = repositoryService.findById(id)?.toDto()
}
