object Config {

    object Core {
        private const val version = "1.7.0"

        const val core = "androidx.core:core-ktx:$version"
    }

    object Activity {
        private const val version = "1.4.0"

        const val activityCompose = "androidx.activity:activity-compose:$version"
    }

    object Compose {
        const val version = "1.1.1" // It is not private. It is used in build.gradle.kts

        const val ui = "androidx.compose.ui:ui:$version"
        const val material = "androidx.compose.material:material:$version"
        const val preview = "androidx.compose.ui:ui-tooling-preview:$version"
        const val liveData = "androidx.compose.runtime:runtime-livedata:$version"
    }

    object Navigation {
        private const val version = "2.4.2"

        const val navigation = "androidx.navigation:navigation-compose:$version"
    }

    object LifeCycle {
        private const val version = "2.4.1"

        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
    }

    object Firebase {
        private const val bomVersion = "30.1.0"

        const val bom = "com.google.firebase:firebase-bom:$bomVersion"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val database = "com.google.firebase:firebase-database-ktx"
    }

    object Koin {
        private const val version = "3.1.6"

        const val android = "io.insert-koin:koin-android:$version"
        const val compose = "io.insert-koin:koin-androidx-compose:$version"
    }

    object Coil {
        private const val version = "2.1.0"

        const val coil = "io.coil-kt:coil:$version"
        const val compose = "io.coil-kt:coil-compose:$version"
    }
}
