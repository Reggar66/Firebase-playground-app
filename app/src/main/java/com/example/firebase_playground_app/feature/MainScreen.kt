package com.example.firebase_playground_app.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebase_playground_app.feature.counter.CounterScreen
import com.example.firebase_playground_app.ui.theme.FirebaseplaygroundappTheme

@Composable
fun MainScreen() {
    FirebaseplaygroundappTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            // Takes care of changing screen based on route
            AppNavHost()
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = CounterScreen.route) {
        composable(CounterScreen.route) { CounterScreen.Content() }
    }
}


abstract class Screen(val route: String) {
    @Composable
    abstract fun Content()
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}