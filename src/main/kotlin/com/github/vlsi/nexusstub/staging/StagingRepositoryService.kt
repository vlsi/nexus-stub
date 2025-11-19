package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.store.XdRepositoryState
import com.github.vlsi.nexusstub.staging.store.XdStagingProfile
import com.github.vlsi.nexusstub.staging.store.XdStagingRepository
import com.github.vlsi.nexusstub.staging.store.toDto
import kotlinx.dnq.query.filter
import kotlinx.dnq.query.firstOrNull
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StagingRepositoryService() {
    private val log = LoggerFactory.getLogger(javaClass)

    fun findById(id: String) =
        XdStagingRepository.filter { it.id eq id }.firstOrNull()

    @Transactional(readOnly = true)
    fun getById(id: String): StagingProfileRepository? =
        findById(id)?.toDto()

    fun create(profile: XdStagingProfile) =
        XdStagingRepository.new {
            val repoName =
                profile.name.replace(Regex("\\."), "") + "-" + System.currentTimeMillis()
            this.id = repoName
            this.profile = profile
            this.type = XdRepositoryState.OPEN
        }

    @Transactional
    fun dropRepositories(repoIds: Array<String>) {
        for (repoId in repoIds) {
            log.info("Dropping staged repository {}", repoId)
            findById(repoId)?.delete()
                ?: throw IllegalArgumentException("Repository $repoId is not found")
        }
    }

    @Transactional
    fun closeRepositories(repoIds: Array<String>) {
        for (repoId in repoIds) {
            log.info("Closing staged repository {}", repoId)
            val repo = findById(repoId)
                ?: throw IllegalArgumentException("Repository $repoId is not found")
            if (repo.type != XdRepositoryState.OPEN) {
                throw IllegalStateException("Repository $repoId should be OPEN, however its state is ${repo.type}")
            }
            repo.type = XdRepositoryState.CLOSED
        }
    }

    @Transactional
    fun promoteRepositories(repoIds: Array<String>) {
        for (repoId in repoIds) {
            log.info("Releasing staged repository {}", repoId)
            val repo = findById(repoId)
                ?: throw IllegalArgumentException("Repository $repoId is not found")
            if (repo.type != XdRepositoryState.CLOSED) {
                throw IllegalStateException("Repository $repoId should be CLOSED, however its state is ${repo.type}")
            }
            repo.type = XdRepositoryState.RELEASED
        }
    }
}
