plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.themagicsportslami.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.themagicsportslami.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 9
        versionName = "1.4.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("sportslami-release.jks")
            storePassword = "sportslamipass"
            keyAlias = "sportslami"
            keyPassword = "sportslamipass"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)

    // Jetpack Glance (Widgets)
    implementation(libs.androidx.glance)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)

    // Background updates & Storage
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.datastore.preferences)

    // Network
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Image loading
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    testImplementation(libs.junit)
    testImplementation("org.json:json:20240303")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
}

tasks.register("copyReleaseArtifacts") {
    group = "distribution"
    description = "Copies signed release APK and AAB bundle to root releases/ directory"
    doLast {
        val releaseDir = rootProject.file("releases")
        if (!releaseDir.exists()) {
            releaseDir.mkdirs()
        }
        val vName = android.defaultConfig.versionName ?: "1.0.0"

        val buildOutputDir = project.layout.buildDirectory.asFile.get()
        val sourceApk = File(buildOutputDir, "outputs/apk/release/app-release.apk")
        if (sourceApk.exists()) {
            val targetApk = File(releaseDir, "TheMagicScorlami-v$vName-release.apk")
            val latestApk = File(releaseDir, "TheMagicScorlami-latest.apk")
            sourceApk.copyTo(targetApk, overwrite = true)
            sourceApk.copyTo(latestApk, overwrite = true)
            println("Successfully placed Release APK under releases: ${targetApk.name}")
        }

        val sourceAab = File(buildOutputDir, "outputs/bundle/release/app-release.aab")
        if (sourceAab.exists()) {
            val targetAab = File(releaseDir, "TheMagicScorlami-v$vName-release.aab")
            val latestAab = File(releaseDir, "TheMagicScorlami-latest.aab")
            sourceAab.copyTo(targetAab, overwrite = true)
            sourceAab.copyTo(latestAab, overwrite = true)
            println("Successfully placed Release Bundle under releases: ${targetAab.name}")
        }
    }
}

afterEvaluate {
    tasks.findByName("assembleRelease")?.finalizedBy("copyReleaseArtifacts")
    tasks.findByName("bundleRelease")?.finalizedBy("copyReleaseArtifacts")

    tasks.register("buildReleaseApk") {
        group = "distribution"
        description = "Builds signed release APK and places it under releases/"
        dependsOn("assembleRelease")
    }
}


