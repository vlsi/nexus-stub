package com.github.vlsi.nexusstub.micronaut.jackson.codec

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

@Factory
class XmlMapperFactory {
    /**
     * Builds the Jackson XML [XmlMapper].
     *
     * @return The [XmlMapper]
     */
    @Bean
    @Singleton
    internal fun xmlMapper() = XmlMapper().apply {
        registerKotlinModule()
    }
}
