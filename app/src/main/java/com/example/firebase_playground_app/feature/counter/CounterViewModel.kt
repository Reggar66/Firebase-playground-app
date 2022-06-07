package com.example.firebase_playground_app.feature.counter

import androidx.lifecycle.ViewModel
import com.example.firebase_playground_app.model.Thing
import com.example.firebase_playground_app.repository.Database

class CounterViewModel : ViewModel() {




    fun addThing(thingId: String, thing: Thing) {
        val id = Database.ref.push().key
        if (id != null) {
            Database.ref.child("items").child(id).setValue(thing)
        }
    }
}