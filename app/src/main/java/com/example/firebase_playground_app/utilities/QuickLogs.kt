package com.example.firebase_playground_app.utilities

import android.util.Log

fun dlog(TAG: String = "DebugLog", msg: () -> Any?) = Log.d(TAG, msg().toString())