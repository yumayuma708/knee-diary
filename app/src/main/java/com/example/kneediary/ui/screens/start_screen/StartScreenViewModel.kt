package com.example.kneediary.ui.screens.start_screen

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

data class TodayState(
    val firstDieValue: Int? = null,
    val secondDieValue: Int? = null,
    val numberOfRolls: Int = 0,
)

class StartScreenViewModel : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth

    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    task.exception?.let { exception ->
                        onError(exception)
                    }
                }
            }
    }
}
