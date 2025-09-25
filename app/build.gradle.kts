// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // This block should only appear once at the very top of the build.gradle.kts file.
    // It applies the Android Application plugin using the version defined in libs.versions.toml.
    alias(libs.plugins.android.application)
}

android {
    // This is the main 'android' configuration block.
    // All properties related to your Android app build (compileSdk, defaultConfig, buildTypes, compileOptions)
    // should be inside this single block.

    // Specifies the Android API level to compile your application against.
    // Changed from 'compileSdk 35' to 'compileSdk = 35' to use the correct Kotlin DSL assignment syntax.
    compileSdk = 35

    // The 'namespace' is a new requirement for Android Gradle Plugin 7.0 and higher.
    // It defines the Java/Kotlin package name for the generated R class and other build artifacts.
    // It should typically match your application's base package.
    namespace = "com.example.foodapp" // Added this line to specify the namespace.

    // Configuration for the default variant of your application.
    defaultConfig {
        // The unique application ID for your app.
        applicationId = "com.example.foodapp"
        // The minimum Android API level required to run your app.
        minSdk = 21
        // The Android API level your app is designed to run on.
        // You can keep targetSdk at 34, or update it to 35 after testing.
        targetSdk = 34
        // A version number for your app, used internally.
        versionCode = 1
        versionName = "1.0"

        // The test instrumentation runner for your Android tests.
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Configuration for different build types (e.g., debug, release).
    buildTypes {
        // Configuration for the 'release' build type.
        release {
            // Disables code shrinking, optimization, and obfuscation for the release build.
            isMinifyEnabled = false
            // Specifies ProGuard rules files to apply for code shrinking and obfuscation.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Configuration for Java compilation options.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // This block configures all tasks of type JavaCompile (which includes your app's Java/Kotlin compilation).
    // This is a more reliable way to add compiler arguments.
    tasks.withType(org.gradle.api.tasks.compile.JavaCompile::class.java).configureEach {
        // Add the -Xlint:deprecation flag to show detailed deprecation warnings.
        options.compilerArgs.add("-Xlint:deprecation")
    }
} // This is the *single* closing brace for the 'android' block. It must enclose both compileOptions and dependencies.

dependencies {
    // All your project dependencies go here.

    // AndroidX UI components.
    implementation(libs.appcompat) // AppCompat library for backward compatibility.
    implementation(libs.material) // Material Design components.
    implementation(libs.activity) // Components for managing activities.
    implementation(libs.constraintlayout) // ConstraintLayout for flexible UI layouts.

    // IMPORTANT: If you are using CardView (e.g., in item_category.xml),
    // you MUST include the CardView dependency.

    // The 'libs.cardview' reference was causing an "Unresolved reference" error,
    // indicating it might not be correctly defined in your libs.versions.toml file.
    // For now, we've commented out the 'libs.cardview' line and activated a direct dependency
    // to ensure your project builds.
    // // implementation(libs.cardview) // Commented out due to unresolved reference

    // Direct dependency for CardView. Use the latest stable version.
    // You can check the latest version on AndroidX releases page or Maven Central.
    implementation("androidx.cardview:cardview:1.0.0")


    // Test dependencies.
    testImplementation(libs.junit) // JUnit for unit testing.
    androidTestImplementation(libs.ext.junit) // JUnit extensions for Android instrumented tests.
    androidTestImplementation(libs.espresso.core) // Espresso for UI testing.
}
