package com.github.vlsi.nexusstub.micronaut.jackson.codec

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.micronaut.core.io.buffer.ByteBuffer
import io.micronaut.core.io.buffer.ByteBufferFactory
import io.micronaut.core.type.Argument
import io.micronaut.http.MediaType
import io.micronaut.http.codec.CodecConfiguration
import io.micronaut.http.codec.CodecException
import io.micronaut.http.codec.MediaTypeCodec
import io.micronaut.jackson.JacksonConfiguration
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class XmlCodec(
    private val xmlMapper: XmlMapper,
    @Named(CONFIGURATION_QUALIFIER) codecConfiguration: CodecConfiguration?
) : MediaTypeCodec {
    companion object {
        private const val CONFIGURATION_QUALIFIER = "xml"
    }

    private val mediaTypes =
        listOf(MediaType.APPLICATION_XML_TYPE).plus(codecConfiguration?.additionalTypes ?: listOf())

    override fun getMediaTypes() = mediaTypes

    @Throws(CodecException::class)
    override fun <T> decode(type: Argument<T>, inputStream: InputStream): T {
        try {
            return if (type.hasTypeVariables()) {
                val javaType = constructJavaType(type)
                xmlMapper.readValue<T>(inputStream, javaType)
            } else {
                xmlMapper.readValue(inputStream, type.type)
            }
        } catch (e: IOException) {
            throw CodecException("Error decoding XML for type [" + type.getName() + "]: " + e.message)
        }

    }

    @Throws(CodecException::class)
    override fun <T> encode(value: T, outputStream: OutputStream) {
        try {
            xmlMapper.writeValue(outputStream, value)
        } catch (e: IOException) {
            throw CodecException("Error encoding object [" + value + "] to XML: " + e.message)
        }

    }

    @Throws(CodecException::class)
    override fun <T> encode(value: T): ByteArray {
        try {
            return if (value is ByteArray) {
                value
            } else {
                xmlMapper.writeValueAsBytes(value)
            }
        } catch (e: JsonProcessingException) {
            throw CodecException("Error encoding object [" + value + "] to XML: " + e.message)
        }

    }

    @Throws(CodecException::class)
    override fun <T> encode(value: T, allocator: ByteBufferFactory<*, *>): ByteBuffer<*> {
        val bytes = encode(value)
        return allocator.copiedBuffer(bytes)
    }

    private fun <T> constructJavaType(type: Argument<T>): JavaType {
        val typeFactory = xmlMapper.typeFactory
        return JacksonConfiguration.constructType(type, typeFactory)
    }
}