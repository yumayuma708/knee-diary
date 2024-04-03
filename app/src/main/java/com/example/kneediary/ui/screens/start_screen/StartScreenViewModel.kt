package com.example.kneediary.ui.screens.start_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kneediary.data.repositories.AuthRepository
import kotlinx.coroutines.launch

class StartScreenViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                val user = authRepository.signInWithEmailAndPassword(email, password)
                if (user != null) {
                    onSuccess()
                } else {
                    onError(Exception("Authentication failed"))
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
    // LiveDataを使用してUIに遷移イベントを通知
    private val _navigateToSignUp = MutableLiveData<Event<Unit>>()
    val navigateToSignUp: LiveData<Event<Unit>> = _navigateToSignUp

    fun onSignUpClicked() {
        // 新規登録ボタンがクリックされた時にイベントを発行
        _navigateToSignUp.value = Event(Unit)
    }
}

class StartScreenViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StartScreenViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

open class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
