package com.example.firebase_playground_app.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Thing(val name: String? = null, val amount: Long? = null, val color: String? = null) {

    @Exclude
    fun toMap() = mapOf<String, Any?>("name" to name, "amount" to amount, "color" to color)
}