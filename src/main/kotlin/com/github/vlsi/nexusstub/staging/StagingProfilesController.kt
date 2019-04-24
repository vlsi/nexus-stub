package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*

@Controller(
    "\${nexusstub.contextpath}service/local/staging/profiles",
    consumes = [MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON],
    produces = [MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON]
)
class StagingProfilesController(
    private val profiles: StagingProfileRepository,
    private val xmlMapper: XmlMapper
) {
    fun <V> HttpHeaders.autodetectType(v: V) =
        HttpResponse.ok(v)
            .contentType(accept().first())

    @Get("/")
    fun index(headers: HttpHeaders) = headers.autodetectType(StagingProfiles().apply {
        data.addAll(profiles.findAll())
    })

    @Get("/{id}")
    fun getProfile(id: String, headers: HttpHeaders) =
        headers.autodetectType(ProfileResponse(profiles.findById(id)))

    @Put("/{id}")
    fun putProfile(id: String, @Body body: ByteArray) =
        ProfileResponse(
            profiles.putById(
                id,
                xmlMapper.readValue(body, ProfileRequest::class.java).data
            )
        )

    @Put("put2/{id}")
    fun putProfile2(id: String, @Body body: ProfileRequest) =
        ProfileResponse(
            profiles.putById(
                id,
                body.data
            )
        )

    @Delete("/{id}")
    fun deleteProfile(id: String) = profiles.deleteById(id)
}