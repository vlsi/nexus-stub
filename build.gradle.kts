import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    java
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    // Don't apply the plugin only when explicitly asked
    id("org.springframework.boot") version "3.5.7"
    id("com.google.cloud.tools.jib") version "3.5.0"
}

version = "2.0.0"
group = "com.github.vlsi.nexusstub"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework:spring-tx")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.jetbrains.xodus:xodus-openAPI:2.0.1")
    implementation("org.jetbrains.xodus:xodus-environment:2.0.1")
    implementation("org.jetbrains.xodus:xodus-entity-store:2.0.1")
    implementation("org.jetbrains.xodus:xodus-vfs:2.0.1")
    implementation("org.jetbrains.xodus:dnq:2.0.0")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.jetbrains.spek:spek-api:1.1.5")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

jib {
    from {
        // Explicitly specify base image - use a multi-arch compatible base
        image = "gcr.io/distroless/java21-debian12"
        // Multi-arch platforms for registry builds
        // Note: jibDockerBuild ignores this and uses native architecture
        platforms {
            platform {
                architecture = "amd64"
                os = "linux"
            }
            platform {
                architecture = "arm64"
                os = "linux"
            }
        }
    }
    container {
        jvmFlags = listOf(
            "-Xms128m",
            "-Xmx512m",  // Set max heap size
            "-XX:+UseContainerSupport",  // Better container resource awareness
            "-XX:MaxRAMPercentage=75.0"  // Use up to 75% of container memory
        )
        mainClass = "com.github.vlsi.nexusstub.ApplicationKt"
        ports = listOf("8080/tcp")
        creationTime = "USE_CURRENT_TIMESTAMP"  // Better for reproducible builds
    }
    to {
        image = "vlsi/nexus-stub"
        tags =
            version.toString().split('.')
                .runningReduceIndexed { index, acc, part ->
                    if (index == 0) part else "$acc.$part"
                }.mapTo(mutableSetOf()) { "v$it" }
        auth {
            // Only set credentials if properties exist
            setUsername(providers.gradleProperty("docker.username"))
            setPassword(providers.gradleProperty("docker.password"))
        }
    }
}

val jvmTargetRelease = 21;

tasks.withType<JavaCompile>().configureEach {
    options.release.set(jvmTargetRelease)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(jvmTargetRelease.toString())
        javaParameters = true
        freeCompilerArgs.add("-Xjdk-release=$jvmTargetRelease")
        freeCompilerArgs.add("-Xannotation-default-target=param-property")
    }
}

// use JUnit 5 platform
tasks.test {
    useJUnitPlatform()
}
