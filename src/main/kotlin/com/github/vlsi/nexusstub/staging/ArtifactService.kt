package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.store.XdArtifact
import com.github.vlsi.nexusstub.staging.store.XdStagingRepository
import com.github.vlsi.nexusstub.staging.store.toDto
import kotlinx.dnq.query.filter
import kotlinx.dnq.query.toList
import org.springframework.stereotype.Service

@Service
class ArtifactService {
    fun findByGroupAndArtifact(repository: XdStagingRepository, groupId: String, artifactId: String) =
        XdArtifact.filter { (it.groupId eq groupId) and (it.artifactId eq artifactId) }
            .toList()
            .asSequence()
            .map { it.toDto() }
            .toList()
}
