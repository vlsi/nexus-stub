package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.store.XdArtifact
import com.github.vlsi.nexusstub.staging.store.XdStagingRepository
import com.github.vlsi.nexusstub.staging.store.toDto
import kotlinx.dnq.query.filter
import kotlinx.dnq.query.toList
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
class ArtifactService(
    private val repositoryService: StagingRepositoryService
) {
    private val log = LoggerFactory.getLogger(javaClass)
    fun findByGroupAndArtifact(repository: XdStagingRepository, groupId: String, artifactId: String) =
        XdArtifact.filter { (it.groupId eq groupId) and (it.artifactId eq artifactId) }
            .toList()
            .map { it.toDto() }

    @Transactional(readOnly = true)
    fun getMetadata(repositoryId: String, path: String): MavenMetadata {
        // Path should be in format: {groupId}/{artifactId}/maven-metadata.xml
        if (!path.endsWith("/maven-metadata.xml")) {
            throw IllegalArgumentException("Path must end with /maven-metadata.xml")
        }

        val pathWithoutSuffix = path.substring(0, path.length - "/maven-metadata.xml".length)
        val lastSlashIndex = pathWithoutSuffix.lastIndexOf('/')
        if (lastSlashIndex == -1) {
            throw IllegalArgumentException("Invalid path format")
        }

        val groupId = pathWithoutSuffix.substring(0, lastSlashIndex).replace('/', '.')
        val artifactId = pathWithoutSuffix.substring(lastSlashIndex + 1)

        val repository = repositoryService.findById(repositoryId)
            ?: throw IllegalArgumentException("Repository $repositoryId does not exist")
        val artifacts = findByGroupAndArtifact(repository, groupId, artifactId)

        val dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val versioning = if (artifacts.isEmpty()) {
            null
        } else {
            // TODO: implement Maven comparison rules
            val versions = artifacts.map { it.version }
            val latest = versions.max()
            MavenMetadataVersioning(
                latest = latest,
                release = latest,
                versions = versions,
                lastUpdated = artifacts.maxBy { it.updatedWhen }.updatedWhen.format(dateFormat)
            )
        }
        return MavenMetadata(
            groupId = groupId,
            artifactId = artifactId,
            versioning = versioning
        )
    }

    @Transactional
    fun deployArtifact(repositoryId: String, path: String, contents: ByteArray, requestURL: String) {
        log.info("Received {} bytes for {}", contents.size, requestURL)
        // TODO: Implement artifact storage logic
    }
}
