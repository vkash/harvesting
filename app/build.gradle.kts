import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.devtoolsKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.serialization)
    alias(libs.plugins.spotless)
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
}

val keystorePropertiesFile = File(rootDir, "../keystore.properties")
val keystoreProperties =
    Properties().apply {
        keystorePropertiesFile.inputStream().use { inputStream ->
            load(inputStream)
        }
    }

android {
    namespace = "ua.com.vkash.harvesting"
    compileSdk = 36

    defaultConfig {
        applicationId = "ua.com.vkash.harvesting"
        minSdk = 24
        targetSdk = 36
        versionCode = 100
        versionName = "1.0.0"
        setProperty("archivesBaseName", "${applicationId}_$versionName")
        ndk {
            //noinspection ChromeOsAbiSupport
            abiFilters += listOf("armeabi-v7a", "arm64-v8a")
        }
    }

    signingConfigs {
        register("release") {
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/licenses/**"
            excludes += "/META-INF/NOTICE.md"
            excludes += "/META-INF/**/LICENSE.txt"
        }
    }
    buildTypes {
        debug {
            isDebuggable = true
            versionNameSuffix = "-debug"
            ndk {
                abiFilters += listOf("x86", "x86_64")
            }
        }
        release {
            versionNameSuffix = "-release"
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target("src/*/kotlin/**/*.kt", "src/*/java/**/*.kt")
        ktlint("1.6.0")
            .customRuleSets(
                listOf(
                    "io.nlopez.compose.rules:ktlint:0.4.22"
                )
            )
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)
    implementation(projects.feature.appmenu)
    implementation(projects.feature.settings)
    implementation(projects.feature.calculator)
    implementation(projects.feature.sku)
    implementation(projects.feature.timesheet)
    implementation(projects.feature.harvest)
    implementation(projects.feature.brigade)
    implementation(projects.feature.fields)
    implementation(projects.feature.reports)
    implementation(projects.feature.works)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.dagger.hilt.android)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.dagger.hilt.compiler)
}
