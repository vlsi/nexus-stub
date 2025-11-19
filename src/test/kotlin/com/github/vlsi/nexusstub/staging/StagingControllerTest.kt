package com.github.vlsi.nexusstub.staging

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(StagingController::class)
class StagingControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockitoBean
    lateinit var stagingProfile: StagingProfileService

    @MockitoBean
    lateinit var artifactService: ArtifactService

    @Test
    fun metadata() {
        mockMvc.perform(
            get("/service/local/staging/deployByRepositoryId/comacme-1055/com/acme/core/maven-metadata.xml")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }
}
