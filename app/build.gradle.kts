import com.baka3k.build.FlavorDimension
import com.baka3k.build.Flavor

plugins {
    id("hi.android.application")
//    id("hi.android.application.compose")
//    id("hi.android.application.jacoco")
    kotlin("kapt")
//    id("jacoco")
    id("dagger.hilt.android.plugin")
//    id("hi.spotless")
}

android {
//    compileSdk = 32
    defaultConfig {
        applicationId = "com.baka3k.jetpackcompose"
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = ".debug"
        }
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    flavorDimensions += FlavorDimension.contentType.name
    productFlavors {
        Flavor.values().forEach {
            create(it.name) {
                dimension = it.dimension.name
                if (it.applicationIdSuffix != null) {
                    applicationIdSuffix = it.applicationIdSuffix
                }
            }
        }
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)

    implementation(libs.hilt.android)
    implementation(project(mapOf("path" to ":core:core-model")))
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)


    api(libs.androidx.hilt.navigation.compose)
    api(libs.androidx.navigation.compose)
//    implementation(project(path = ":code:core-ui"))
    implementation(project(path = ":core:core-network"))
    implementation(project(path = ":core:core-data"))
    implementation(project(path = ":core:core-common"))
    implementation(project(path = ":features:feature-movie"))

//    implementation(libs.kotlinx.serialization.json)

//    implementation(project(path = ":core-ui"))
//    implementation(project(path = ":core-navigation"))
//    implementation(project(path = ":feature-foryou"))
//    implementation(project(path = ":feature-interests"))
//    implementation(project(path = ":feature-topic"))
//    implementation(project(path = ":feature-author"))
//    implementation(project(path = ":feature-movies"))
//    implementation(project(path = ":sync"))
}