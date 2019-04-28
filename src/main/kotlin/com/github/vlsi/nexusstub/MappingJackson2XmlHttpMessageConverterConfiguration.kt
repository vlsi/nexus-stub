package com.github.vlsi.nexusstub

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter

@Configuration
class MappingJackson2XmlHttpMessageConverterConfiguration {
    @Bean
    fun mappingJackson2XmlHttpMessageConverter(builder: Jackson2ObjectMapperBuilder) =
        MappingJackson2XmlHttpMessageConverter(
            builder.createXmlMapper(true)
                .build<XmlMapper>()
                .enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION) // Adds `<?xml version="1.0" encoding="UTF-8"?>` XML header
        )
}