plugins {
    id("hi.android.library")
    id("hi.android.library.compose")
    id("hi.android.library.jacoco")
    id("hi.spotless")
}

dependencies {
    implementation(project(":core:core-model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.kotlinx.datetime)

    // TODO : Remove these dependency once we upgrade to Android Studio Dolphin b/228889042
    // These dependencies are currently necessary to render Compose previews
    debugImplementation(libs.androidx.fragment)
    debugImplementation(libs.androidx.lifecycle.viewModelCompose)
    debugImplementation(libs.androidx.savedstate.ktx)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.runtime.livedata)

    api(libs.androidx.fragment)
    api(libs.androidx.fragment.ktx)
}