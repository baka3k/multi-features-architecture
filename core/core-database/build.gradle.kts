@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("hi.android.library")
    kotlin("kapt")
    alias(libs.plugins.ksp)
}

android {
    defaultConfig {
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
    namespace = "com.baka3k.core.database"
}

dependencies {
    implementation(project(":core:core-model"))
    implementation(project(":data:database:movie"))

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.hilt.android)

    ksp(libs.room.compiler)
    kapt(libs.hilt.compiler)
}