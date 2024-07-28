plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.ksp)
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

android {
    namespace = "com.example.kneediary"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kneediary"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    // 1.5.1かも?
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        sarifOutput = File(project.buildDir, "reports/android-lint/lintResults.sarif")
        textOutput = File(project.buildDir, "reports/android-lint/lintResults.txt")
        htmlOutput = File(project.buildDir, "reports/android-lint/lintResults.html")
        xmlReport = false
    }
}

@Suppress("ktlint:standard:property-naming")
val compose_version: String by rootProject.extra

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:model"))
    implementation(libs.androidx.core.ktx)
    implementation("org.jetbrains.kotlin:kotlin-bom:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation(libs.androidx.appcompat)
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("com.google.ai.client.generativeai:generativeai:0.2.2")

    implementation(libs.hilt.android)
    implementation(libs.androidx.media3.common)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.android.compiler)
    implementation(libs.activity.compose)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.hilt.navigation.compose)
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.5")
    implementation("androidx.compose.material:material-icons-extended:1.6.5")

    lintChecks("com.slack.lint.compose:compose-lint-checks:1.3.1")

//    implementation("com.google.dagger:dagger-producers:2.51.1")

    implementation("androidx.glance:glance-appwidget:1.1.0")

    implementation("androidx.glance:glance-material3:1.1.0")

    implementation("androidx.glance:glance-material:1.1.0")
}
