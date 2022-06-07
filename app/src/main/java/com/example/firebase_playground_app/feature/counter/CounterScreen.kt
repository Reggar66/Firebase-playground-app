package com.example.firebase_playground_app.feature.counter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebase_playground_app.feature.Screen
import com.example.firebase_playground_app.model.Thing

object CounterScreen : Screen("counter") {
    @Composable
    override fun Content() {
        CounterScreen()
    }
}


@Composable
fun CounterScreen(viewModel: CounterViewModel = viewModel()) {

    var id by remember {
        mutableStateOf(0)
    }

    Box(Modifier.fillMaxSize()) {

        Button(modifier = Modifier.align(Alignment.BottomEnd), onClick = {
            viewModel.addThing(
                id.toString(),
                Thing("Thing name $id", amount = id.toLong())
            )
            id++
        }) {
            Text(text = "Add")
        }
    }
}

@Preview
@Composable
private fun CounterScreenPreview() {
    CounterScreen()
}