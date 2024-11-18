plugins {
    id("com.google.gms.google-services")
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.pcr.procookingrecipes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pcr.procookingrecipes"
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
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        pickFirst("META-INF/INDEX.LIST")
        exclude("META-INF/INDEX.LIST")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.volley)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.play.services.auth)
    implementation("com.google.cloud:google-cloud-translate:2.53.0") {
        exclude(group = "com.google.api.grpc", module = "proto-google-common-protos")
        exclude(group = "com.google.firebase", module = "protolite-well-known-types")
        exclude(group = "com.google.protobuf", module = "protobuf-java")
        exclude(group = "com.google.guava", module = "guava")
        exclude(group = "org.apache.httpcomponents", module = "httpclient")
    }

    implementation("com.google.firebase:firebase-auth:23.1.0") {
        exclude(group = "com.google.api.grpc", module = "proto-google-common-protos")
        exclude(group = "com.google.firebase", module = "protolite-well-known-types")
    }

    // Añade Firebase Firestore
    implementation (libs.firebase.firestore)
    // Añade Android Material
    implementation (libs.material.v190)
    implementation (libs.okhttp)
    implementation ("com.squareup.picasso:picasso:2.8")
}