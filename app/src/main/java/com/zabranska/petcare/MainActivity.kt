package com.zabranska.petcare // Залиш свій пакет

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Всі можливі стани екранів
enum class ScreenState {
    Splash, Onboarding, Welcome, Login, Register, MainApp
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userManager = UserManager(this) // Створюємо тут, щоб передати у фабрику

        setContent {
            var currentScreen by remember { mutableStateOf(ScreenState.Splash) }

            when (currentScreen) {
                // --- 1. SPLASH З VIEWMODEL ---
                ScreenState.Splash -> {
                    // Ініціалізуємо ViewModel за допомогою фабрики
                    val splashViewModel: SplashViewModel = viewModel(
                        factory = SplashViewModelFactory(userManager)
                    )

                    // Спостерігаємо за рішенням ViewModel
                    val destination by splashViewModel.destination.collectAsState()

                    // Відображаємо сам екран Splash (картинку)
                    WelcomeScreen(onTimeout = {}) // onTimeout тепер пустий, бо логіка у ViewModel

                    // Як тільки destination зміниться (перестане бути null) - переходимо
                    LaunchedEffect(destination) {
                        destination?.let {
                            currentScreen = it
                        }
                    }
                }

                ScreenState.Onboarding -> {
                    OnboardingScreen(
                        onFinished = {
                            userManager.saveOnboardingFinished()
                            currentScreen = ScreenState.Welcome
                        },
                        onSignInClick = {
                            userManager.saveOnboardingFinished()
                            currentScreen = ScreenState.Login
                        }
                    )
                }

                ScreenState.Welcome -> {
                    WelcomeAuthScreen(
                        onNavigateToLogin = { currentScreen = ScreenState.Login },
                        onNavigateToRegister = { currentScreen = ScreenState.Register }
                    )
                }

                ScreenState.Register -> {
                    RegistrationScreen(
                        onBack = { currentScreen = ScreenState.Welcome },
                        onRegisterSuccess = { name, email, pass ->
                            userManager.registerUser(name, email, pass)
                            currentScreen = ScreenState.MainApp
                        }
                    )
                }

                ScreenState.Login -> {
                    LoginScreen(
                        onBack = { currentScreen = ScreenState.Welcome },
                        onLoginAttempt = { email, pass ->
                            if (userManager.loginUser(email, pass)) {
                                userManager.registerUser("User", email, pass)
                                currentScreen = ScreenState.MainApp
                            } else {
                                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }

                // --- 2. ГОЛОВНИЙ ЕКРАН (ОНОВЛЕНО) ---
                ScreenState.MainApp -> {
                    MainAppScreen() // Викликаємо наш новий екран з навігацією
                }
            }
        }
    }
}