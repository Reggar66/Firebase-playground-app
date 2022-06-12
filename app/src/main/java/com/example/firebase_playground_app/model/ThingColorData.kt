package com.example.firebase_playground_app.model

import androidx.compose.ui.graphics.Color

data class ThingColorData(val thingColor: ThingColor, var selected: Boolean)


/**
 * Enum class for colors used in counters
 */
enum class ThingColor(val color: Color) {
    // Using color 700
    Red(Color(0xFFD32F2F)),
    Pink(Color(0xFFC2185B)),
    Purple(Color(0xFF7B1FA2)),
    DeepPurple(Color(0xFF512DA8)),
    Indigo(Color(0xFF303F9F)),
    Blue(Color(0xFF1976D2)),
    Cyan(Color(0xFF0097A7)),
    Teal(Color(0xFF00796B)),
}