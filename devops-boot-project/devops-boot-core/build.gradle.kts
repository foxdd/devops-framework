plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
}

description = "DevOps Boot"

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "publish.jar")

    dependencyManagement {
        imports {
            mavenBom(MavenBom.SpringBoot)
            mavenBom(MavenBom.SpringCloud)
        }
    }

    dependencies {
        implementation(Libs.KotlinStdLib)
        implementation("org.springframework.boot:spring-boot-starter")
        kapt("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    tasks {
        compileKotlin {
            kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
            kotlinOptions.jvmTarget = Versions.Java
        }
        compileTestKotlin {
            kotlinOptions.jvmTarget = Versions.Java
        }
        test {
            useJUnitPlatform()
        }
        jar {
            manifest {
                attributes("Implementation-Title" to (project.description ?: project.name))
                attributes("Implementation-Version" to Release.Version)
            }
        }
    }
}