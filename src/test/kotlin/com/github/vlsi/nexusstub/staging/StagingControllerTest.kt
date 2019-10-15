package com.github.vlsi.nexusstub.staging

import com.github.vlsi.nexusstub.staging.StagingController
import com.github.vlsi.nexusstub.staging.StagingProfileService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(StagingController::class)
class StagingControllerTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockBean
    lateinit var stagingProfile: StagingProfileService

    @Test
    fun metadata() {
        mockMvc.perform(
            get("/service/local/staging/deployByRepositoryId/comacme-1055/com/acme/core/maven-metadata.xml")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }
}
