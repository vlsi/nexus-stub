package com.github.vlsi.nexusstub.staging

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    "service/local/staging/profiles",
    produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
)
class StagingProfilesController(
    private val profiles: StagingProfileService
) {

    @GetMapping
    fun profiles() = StagingProfiles().apply {
        data.addAll(profiles.findAll())
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String) = profiles.getById(id)

    @PutMapping("/{id}")
    fun put(@PathVariable id: String, @RequestBody body: ProfileRequest) =
        profiles.updateProfile(id, body)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) = profiles.deleteById(id)

    @PostMapping("/{id}/start")
    fun start(@PathVariable id: String) =
        profiles.start(id, StagingPromoteDto(StagingPromote()))

}
