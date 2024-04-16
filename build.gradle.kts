buildscript{
}
ext {
    extra["compose_version"] = "1.2.0"
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "8.2.0" apply false
    id ("com.android.library") version "8.2.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
//    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    kotlin("jvm") version "1.9.0" apply false
    id ("com.github.ben-manes.versions") version "0.41.0"
    id ("nl.littlerobots.version-catalog-update") version "0.8.0"

    id("androidx.room") version "2.6.1" apply false

}
