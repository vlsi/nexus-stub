package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.store.XdRepositoryState
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalStateException

@RestController
@RequestMapping(
    "service/local/staging/bulk",
    produces = [MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE]
)
class StagingBulkController(
    private val repositoryService: StagingRepositoryService
) {
    @PostMapping("drop")
    @Transactional
    fun drop(@RequestBody request: StagingBulkDto) {
        if (request.data.stagedRepositoryIds.isEmpty()) {
            throw IllegalArgumentException("No stagedRepositoryIds received")
        }
        for (repoId in request.data.stagedRepositoryIds) {
            println("Dropping staged repository $repoId")
            repositoryService.findById(repoId)?.delete()
                ?: throw IllegalArgumentException("Repository $repoId is not found")
        }
    }

    @PostMapping("close")
    @Transactional
    fun close(@RequestBody request: StagingBulkDto) {
        if (request.data.stagedRepositoryIds.isEmpty()) {
            throw IllegalArgumentException("No stagedRepositoryIds received")
        }
        for (repoId in request.data.stagedRepositoryIds) {
            println("Closing staged repository $repoId")
            val repo = repositoryService.findById(repoId)
                ?: throw IllegalArgumentException("Repository $repoId is not found")
            if (repo.type != XdRepositoryState.OPEN) {
                throw IllegalStateException("Repository $repoId should be OPEN, however its state is ${repo.type}")
            }
            repo.type = XdRepositoryState.CLOSED
        }
    }

    @PostMapping("promote")
    @Transactional
    fun promote(@RequestBody request: StagingBulkDto) {
        if (request.data.stagedRepositoryIds.isEmpty()) {
            throw IllegalArgumentException("No stagedRepositoryIds received")
        }
        for (repoId in request.data.stagedRepositoryIds) {
            println("Releasing staged repository $repoId")
            val repo = repositoryService.findById(repoId)
                ?: throw IllegalArgumentException("Repository $repoId is not found")
            if (repo.type != XdRepositoryState.CLOSED) {
                throw IllegalStateException("Repository $repoId should be CLOSED, however its state is ${repo.type}")
            }
            repo.type = XdRepositoryState.RELEASED
        }
    }
}
