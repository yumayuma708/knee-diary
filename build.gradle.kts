buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
    }
}
ext {
    extra["compose_version"] = "1.2.0"
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
//    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
//    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    kotlin("jvm") version "1.9.0" apply false
    id("com.github.ben-manes.versions") version "0.41.0"
    id("nl.littlerobots.version-catalog-update") version "0.8.0"

    id("androidx.room") version "2.6.1" apply false
    alias(libs.plugins.ksp) apply false
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
