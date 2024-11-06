plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.app.androidassesment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.androidassesment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycleViewModel)
    implementation(libs.lifecycleViewLiveData)
    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
    implementation(libs.glide)
    implementation(libs.okhttp)
    implementation(libs.okhttpLogger)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    api(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.composeVM)
    implementation(libs.room.runtime) // Room runtime
    implementation(libs.room.ktx)     // Room Kotlin extensions
    kapt(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}