plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.rs.codingexercisegyg"
        minSdkVersion(21)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        val options = this
        options.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${LibVersions.kotlinVersion}")

    implementation("androidx.appcompat:appcompat:${LibVersions.appcompatVersion}")
    implementation("androidx.core:core-ktx:${LibVersions.appcompatVersion}")

    implementation("com.google.dagger:dagger:${LibVersions.daggerVersion}")
    implementation("com.google.dagger:dagger-android-support:${LibVersions.daggerVersion}")
    implementation("androidx.constraintlayout:constraintlayout:${LibVersions.constraintLayoutVersion}")
    implementation("androidx.cardview:cardview:${LibVersions.cardLayoutVersion}")
    kapt("com.google.dagger:dagger-compiler:${LibVersions.daggerVersion}")
    kapt("com.google.dagger:dagger-android-processor:${LibVersions.daggerVersion}")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${LibVersions.retrofitVersion}")
    implementation("com.google.code.gson:gson:${LibVersions.gsonVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${LibVersions.retrofitVersion}")

    // Okhttp
    implementation("com.squareup.okhttp3:logging-interceptor:${LibVersions.okhttpVersion}")
    implementation("com.squareup.okhttp3:okhttp:${LibVersions.okhttpVersion}")

    // LiveData and ViewModel
    implementation("android.arch.lifecycle:extensions:${LibVersions.lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${LibVersions.viewModelKtxVersion}")

    // Paging
    implementation("android.arch.paging:runtime:${LibVersions.pagingLibVersion}")

    // Picasso
    implementation("com.squareup.picasso:picasso:${LibVersions.picassoVersion}")

    // testing
    testImplementation("junit:junit:${LibVersions.junitVersion}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${LibVersions.coroutinesTestVersion}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${LibVersions.mockitoVersion}")
    testImplementation("androidx.arch.core:core-testing:${LibVersions.coreTestingArch}")

    // Multidex
    implementation("androidx.multidex:multidex:${LibVersions.multidexVersion}")
}
