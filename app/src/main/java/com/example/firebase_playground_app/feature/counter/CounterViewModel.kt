package com.example.firebase_playground_app.feature.counter

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.firebase_playground_app.model.Thing
import com.example.firebase_playground_app.model.ThingColor
import com.example.firebase_playground_app.model.ThingColorData
import com.example.firebase_playground_app.repository.Database
import com.example.firebase_playground_app.repository.DbPath
import com.example.firebase_playground_app.utilities.dlog
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.callbackFlow

class CounterViewModel : ViewModel() {

    private val auth by lazy { Firebase.auth }
    private var currentUser: FirebaseUser? = auth.currentUser

    private var currentColor: ThingColor = ThingColor.Red

    val things = mutableStateListOf<Pair<String, Thing>>()

    val thingColors = mutableStateListOf<ThingColorData>()

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            dlog { "onChildAdded: ${snapshot.key}" }
            dlog { "Item: " }
            snapshot.children.forEach {
                dlog { "    ${it.key}: ${it.value}" }
            }
            val key = snapshot.key
            val thing = snapshot.getValue<Thing>()
            if (key != null && thing != null)
                things.add(key to thing)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            dlog { "onChildChanged: ${snapshot.key}" }
            dlog { "Item: " }
            snapshot.children.forEach {
                dlog { "    ${it.key}: ${it.value}" }
            }

            val key = snapshot.key
            val thing = snapshot.getValue<Thing>()
            if (key != null && thing != null)
                things.replaceAll {
                    if (it.first == key)
                        key to thing
                    else
                        it
                }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            dlog { "onChildRemoved: ${snapshot.key}" }
            dlog { "Item: " }
            snapshot.children.forEach {
                dlog { "    ${it.key}: ${it.value}" }
            }

            val key = snapshot.key
            val thing = snapshot.getValue<Thing>()
            things.remove(key to thing)
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            dlog { "onChildMoved: ${snapshot.key}" }
            dlog { "Item: " }
            snapshot.children.forEach {
                dlog { "    ${it.key}" }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            dlog { "onChildCancelled: ${error.toException()}" }
        }
    }

    init {
        dlog { "CounterViewModel init(): signedIn: ${currentUser != null}" }
        ThingColor.values().forEach {
            thingColors.add(ThingColorData(it, it == ThingColor.Red))
        }
        Database.ref.child(getItemsPath()).addChildEventListener(listener)
    }

    fun pickColor(color: ThingColor) {
        thingColors.replaceAll {
            if (it.thingColor == color) {
                it.copy(selected = true).also {
                    currentColor = it.thingColor
                }
            } else
                it.copy(selected = false)
        }
    }

    fun addThing(name: String, amount: String) {
        amount.toLongOrNull()?.let {
            val thing = Thing(name, it, currentColor.name)
            val id = Database.ref.push().key
            if (id == null) {
                dlog { "Couldn't get push key for thing." }
                return
            }
            Database.ref.child(getItemsPath()).child(id).setValue(thing)
        }
    }

    fun removeThing(key: String) {
        Database.ref.child(getItemsPath() + "/$key").ref.removeValue()
    }

    fun decrease(item: Pair<String, Thing>) {
        val newAmount = item.second.amount?.minus(1)
        if (newAmount != null) {
            update(item, newAmount)
        }
    }

    fun increase(item: Pair<String, Thing>) {
        val newAmount = item.second.amount?.plus(1)
        if (newAmount != null) {
            update(item, newAmount)
        }
    }

    private fun update(item: Pair<String, Thing>, newAmount: Long) {

        val thing = item.second.copy(amount = newAmount)
        val thingValues = thing.toMap()

        val updates = hashMapOf<String, Any>(
            getItemsPath() + "/${item.first}" to thingValues
        )

        Database.ref.updateChildren(updates)
    }

    private fun getItemsPath() = "/items/UID-${currentUser?.uid}"
}