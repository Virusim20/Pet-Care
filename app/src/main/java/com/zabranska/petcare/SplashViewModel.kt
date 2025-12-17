package com.zabranska.petcare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel відповідає за логіку таймера та перевірки юзера
class SplashViewModel(private val userManager: UserManager) : ViewModel() {

    // Стан, який ми будемо слухати в MainActivity
    private val _destination = MutableStateFlow<ScreenState?>(null)
    val destination = _destination.asStateFlow()

    init {
        startSplashLogic()
    }

    private fun startSplashLogic() {
        viewModelScope.launch {
            delay(1000) // Імітація завантаження (1 сек)

            // Перевіряємо умови
            if (userManager.isUserLoggedIn()) {
                _destination.value = ScreenState.MainApp
            } else if (!userManager.isOnboardingFinished()) {
                _destination.value = ScreenState.Onboarding
            } else {
                _destination.value = ScreenState.Welcome
            }
        }
    }
}

// Фабрика потрібна, щоб передати UserManager у конструктор ViewModel
class SplashViewModelFactory(private val userManager: UserManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(userManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}