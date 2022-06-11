package com.example.firebase_playground_app.feature.counter

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.firebase_playground_app.model.Thing
import com.example.firebase_playground_app.repository.Database
import com.example.firebase_playground_app.repository.DbPath
import com.example.firebase_playground_app.utilities.dlog
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue

class CounterViewModel : ViewModel() {

    val things = mutableStateListOf<Pair<String, Thing>>()

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            dlog { "onChildAdded: ${snapshot.key}" }
            dlog { "List: " }
            snapshot.children.forEach {
                dlog { "    ${it.key}" }
            }
            val key = snapshot.key
            val thing = snapshot.getValue<Thing>()
            if (key != null && thing != null)
                things.add(key to thing)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            dlog { "onChildChanged: ${snapshot.key}" }
            dlog { "List: " }
            snapshot.children.forEach {
                dlog { "    ${it.key}" }
            }

            // TODO update list with changed item
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            dlog { "onChildRemoved: ${snapshot.key}" }
            dlog { "List: " }
            snapshot.children.forEach {
                dlog { "    ${it.key}" }
            }

            val key = snapshot.key
            val thing = snapshot.getValue<Thing>()
            things.remove(key to thing)
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            dlog { "onChildMoved: ${snapshot.key}" }
            dlog { "List: " }
            snapshot.children.forEach {
                dlog { "    ${it.key}" }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            dlog { "onChildCancelled: ${error.toException()}" }
        }
    }

    init {
        Database.ref.child(DbPath.items).addChildEventListener(listener)
    }

    fun addThing(thing: Thing) {
        val id = Database.ref.push().key
        if (id == null) {
            dlog { "Couldn't get push key for thing." }
            return
        }
        Database.ref.child(DbPath.items).child(id).setValue(thing)
    }

    fun removeThing(key: String) {
        Database.ref.child(DbPath.items + "/$key").ref.removeValue()
    }

    fun decrease() {

    }

    fun increase(key: String) {

        // TODO it updates item with given ID. Make it increase value instead of replacing all values
        val thing = Thing("New Name", 333)
        val thingValues = thing.toMap()

        val updates = hashMapOf<String, Any>(
            "/items/$key" to thingValues
        )

        Database.ref.updateChildren(updates)
    }
}