package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.store.toDto
import com.github.vlsi.nexusstub.staging.store.toStagingPromote
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    "service/local/staging/profiles",
    produces = [MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE]
)
class StagingProfilesController(
    private val profiles: StagingProfileService
) {

    @GetMapping
    @Transactional(readOnly = true)
    fun profiles() = StagingProfiles().apply {
        data.addAll(profiles.findAll())
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun get(@PathVariable id: String) =
        ProfileResponse(profiles.findById(id)?.toDto())

    @PutMapping("/{id}")
    @Transactional
    fun put(@PathVariable id: String, @RequestBody body: ProfileRequest) =
        ProfileResponse(
            profiles.put(
                body.data.apply { this.id = id }
            ).toDto()
        )

    @DeleteMapping("/{id}")
    @Transactional
    fun delete(@PathVariable id: String) = profiles.deleteById(id)

    @PostMapping("/{id}/start")
    @Transactional
    fun start(@PathVariable id: String) =
        StagingPromoteDto(
            profiles.start(
                id,
                StagingPromoteDto(StagingPromote())
            ).toStagingPromote()
        )

}
