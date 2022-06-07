package com.example.firebase_playground_app.feature.counter

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.firebase_playground_app.model.Thing
import com.example.firebase_playground_app.repository.Database
import com.example.firebase_playground_app.utilities.dlog
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class CounterViewModel : ViewModel() {

    val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            dlog { "onChildAdded: ${snapshot.key}" }
            snapshot.children.forEach {
                dlog { "    ${it.key}" }
            }
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            dlog { "onChildChanged: ${snapshot.key}" }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            dlog { "onChildRemoved: ${snapshot.key}" }
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            dlog { "onChildMoved: ${snapshot.key}" }
        }

        override fun onCancelled(error: DatabaseError) {
            dlog { "onChildCancelled: ${error.toException()}" }
        }
    }

    init {
        Database.ref.addChildEventListener(listener)
    }

    fun addThing(thingId: String, thing: Thing) {
        val id = Database.ref.push().key
        if (id != null) {
            Database.ref.child("items").child(id).setValue(thing)
        }
    }
}