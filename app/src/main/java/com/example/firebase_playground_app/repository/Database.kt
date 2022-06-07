package com.example.firebase_playground_app.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object Database {

    private val db =
        Firebase.database("https://fir-playground-app-default-rtdb.europe-west1.firebasedatabase.app/")

    val ref get() = db.reference
}