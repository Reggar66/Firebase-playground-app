package com.example.firebase_playground_app.feature.signIn

import androidx.lifecycle.ViewModel
import com.example.firebase_playground_app.utilities.dlog
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInViewModel : ViewModel() {

    private val auth by lazy { Firebase.auth }
    private var currentUser: FirebaseUser? = null

    init {
        currentUser = auth.currentUser
        dlog { "SignInViewModel init(): signedIn: ${currentUser != null}" }
    }

    fun signInAnonymously(onSuccess: () -> Unit) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    dlog { "signInAnonymously(): SUCCESS" }
                    currentUser = auth.currentUser
                    dlog { "User: ${currentUser?.uid}" }
                    onSuccess.invoke()
                } else {
                    // If sign in fails, display a message to the user.
                    dlog { "signInAnonymously(): FAIL" }
                }
            }
    }

    fun signInMail(email: String, password: String, onSuccess: () -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty())
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    dlog { "signInMail(): SUCCESS" }
                    currentUser = auth.currentUser
                    dlog { "User: ${currentUser?.uid}" }
                    onSuccess.invoke()
                } else {
                    dlog { "signInMail(): FAIL" }
                }
            }
    }

    fun signOut() = auth.signOut()
}