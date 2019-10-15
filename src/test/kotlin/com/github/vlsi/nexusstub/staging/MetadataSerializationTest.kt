package com.github.vlsi.nexusstub.staging

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MetadataSerializationTest {
    val metadata = MavenMetadata(
        groupId = "com.acme",
        artifactId = "core",
        versioning = MavenMetadataVersioning(
            latest = "2.0",
            release = "2.0",
            versions = listOf("1.0", "2.0"),
            lastUpdated = "20190313212620"
        )
    )

    @Test
    internal fun xml() {
        val mapper = XmlMapper().enable(SerializationFeature.INDENT_OUTPUT)
        Assertions.assertEquals("""
            <metadata>
              <groupId>com.acme</groupId>
              <artifactId>core</artifactId>
              <versioning>
                <latest>2.0</latest>
                <release>2.0</release>
                <versions>
                  <version>1.0</version>
                  <version>2.0</version>
                </versions>
                <lastUpdated>20190313212620</lastUpdated>
              </versioning>
            </metadata>

        """.trimIndent(), mapper.writeValueAsString(metadata))
    }
}
