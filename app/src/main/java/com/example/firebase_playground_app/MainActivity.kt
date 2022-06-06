package com.example.firebase_playground_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.firebase_playground_app.ui.theme.FirebaseplaygroundappTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseplaygroundappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TestComposableWithFirebase()
                }
            }
        }
    }
}

val db =
    Firebase.database("https://fir-playground-app-default-rtdb.europe-west1.firebasedatabase.app/")

@Composable
fun TestComposableWithFirebase() {

    var read by remember {
        mutableStateOf("")
    }

    Column {
        Button(onClick = {
            db.getReference("message").apply {
                addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val value = snapshot.getValue<String>()
                        read = value.toString()
                        Log.d("INFO", "Value is: $value")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        /*TODO*/
                    }
                })
                setValue("Hello, World!")
            }


        }) {
            Text(text = "Do Firebase DB.")
        }

        Text(text = read)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirebaseplaygroundappTheme {
        TestComposableWithFirebase()
    }
}