// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Các plugin mặc định của Android
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // Khai báo plugin KSP (Kotlin Symbol Processing) cho Room
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
}