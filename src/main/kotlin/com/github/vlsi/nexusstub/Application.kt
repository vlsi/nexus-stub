package com.github.vlsi.nexusstub

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("com.github.vlsi.nexusstub")
            .mainClass(Application.javaClass)
            .start()
    }
}