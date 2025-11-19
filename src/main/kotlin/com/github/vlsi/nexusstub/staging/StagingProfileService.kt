package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.store.XdStagingProfile
import com.github.vlsi.nexusstub.staging.store.toDto
import com.github.vlsi.nexusstub.staging.store.toStagingPromote
import kotlinx.dnq.creator.findOrNew
import kotlinx.dnq.query.filter
import kotlinx.dnq.query.firstOrNull
import kotlinx.dnq.query.toList
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StagingProfileService(
    private val repositoryService: StagingRepositoryService,
    @Value("\${nexusstub.contextpath}") private val contextPath: String
) {
    @Transactional(readOnly = true)
    fun findAll(): List<StagingProfile> =
        XdStagingProfile.all().toList().asSequence()
            .map {
                it.toDto()
            }
            .toList()

    fun findById(id: String) =
        XdStagingProfile.filter { it.id eq id }.firstOrNull()

    @Transactional(readOnly = true)
    fun getById(id: String): ProfileResponse =
        ProfileResponse(findById(id)?.toDto())

    @Transactional
    fun updateProfile(id: String, body: ProfileRequest): ProfileResponse =
        ProfileResponse(
            put(body.data.apply { this.id = id }).toDto()
        )

    @Transactional
    fun deleteById(id: String) =
        XdStagingProfile.filter { it.id eq id }.firstOrNull()?.delete()

    fun put(profile: StagingProfile) =
        XdStagingProfile.findOrNew { this.id = profile.id ?: throw IllegalArgumentException("profile.id is required") }.apply {
            name = profile.name
        }

    @Transactional
    fun start(profileId: String, request: StagingPromoteDto): StagingPromoteDto {
        val profile = findById(profileId)
            ?: throw IllegalArgumentException("Profile not found: $profileId")
        val repository = repositoryService.create(profile)
        return StagingPromoteDto(repository.toStagingPromote())
    }
}
