plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
//    id("com.google.devtools.ksp")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.github.yumayuma708.apps.database"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
}

ksp{
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //4/15に追加
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.10-1.0.13")
//    ksp("androidx.room:room-compiler:2.5.0")

//    implementation(libs.room.runtime)
//    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
}