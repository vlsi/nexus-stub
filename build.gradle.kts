plugins {
    java
    kotlin("jvm") version "1.3.40"
    kotlin("plugin.spring") version "1.3.40"
    // Don't apply the plugin only when explicitly asked
    id("org.springframework.boot") version "2.1.6.RELEASE"
    id("com.google.cloud.tools.jib") version "1.4.0"
}

version = "0.1"
group = "com.github.vlsi.nexusstub"

repositories {
    jcenter()
}

jib {
    container {
        jvmFlags = listOf("-Xms128m")
        mainClass = "com.github.vlsi.nexusstub.ApplicationKt"
        args = listOf()
        ports = listOf("8080/tcp")
    }
    to {
        image = "vlsi/nexus-stub"
        tags = setOf("latest")
    }
}


dependencies {
    compile(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    compile(kotlin("reflect"))
    compile(kotlin("stdlib"))
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework:spring-tx")
    compile("javax.annotation:javax.annotation-api")

    compile("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")

    compile("org.jetbrains.xodus:xodus-openAPI:1.3.0")
    compile("org.jetbrains.xodus:xodus-environment:1.3.0")
    compile("org.jetbrains.xodus:xodus-entity-store:1.3.0")
    compile("org.jetbrains.xodus:xodus-vfs:1.3.0")
    compile("org.jetbrains.xodus:dnq:1.3.424")

    runtime("ch.qos.logback:logback-classic")
    runtime("com.fasterxml.jackson.module:jackson-module-kotlin")

    testCompile("org.junit.jupiter:junit-jupiter-api")
    testCompile("org.jetbrains.spek:spek-api:1.1.5")
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
    testRuntime("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// use JUnit 5 platform
tasks.test {
    useJUnitPlatform()
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileKotlin {
	kotlinOptions {
	    jvmTarget = "1.8"
	    //Will retain parameter names for Java reflection
	    javaParameters = true
	}
}

tasks.compileTestKotlin {
	kotlinOptions {
	    jvmTarget = "1.8"
	    javaParameters = true
	}
}
