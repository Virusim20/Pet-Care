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

// Всі можливі стани екранів
enum class ScreenState {
    Splash, Onboarding, Welcome, Login, Register, MainApp
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userManager = UserManager(this)

        setContent {
            var currentScreen by remember { mutableStateOf(ScreenState.Splash) }

            when (currentScreen) {
                // --- 1. SPLASH SCREEN ---
                ScreenState.Splash -> {
                    WelcomeScreen(onTimeout = {
                        // ЛОГІКА ПЕРЕХОДІВ (маршрутизація)
                        if (userManager.isUserLoggedIn()) {
                            // Якщо вже залогінений -> в головний додаток
                            currentScreen = ScreenState.MainApp
                        } else if (!userManager.isOnboardingFinished()) {
                            // Якщо НЕ бачив онбордінг -> показуємо його
                            currentScreen = ScreenState.Onboarding
                        } else {
                            // Якщо бачив онбордінг, але не залогінений -> екран входу
                            currentScreen = ScreenState.Welcome
                        }
                    })
                }

                // --- 2. ONBOARDING (Лабораторна 3) ---
                ScreenState.Onboarding -> {
                    OnboardingScreen(
                        onFinished = {
                            // Натиснули "Get Started": запам'ятовуємо і йдемо на Welcome
                            userManager.saveOnboardingFinished()
                            currentScreen = ScreenState.Welcome
                        },
                        onSignInClick = {
                            // Натиснули "Sign In" зверху: пропускаємо і йдемо на Login
                            userManager.saveOnboardingFinished()
                            currentScreen = ScreenState.Login
                        }
                    )
                }

                // --- 3. WELCOME (Вибір Login/Register) ---
                ScreenState.Welcome -> {
                    WelcomeAuthScreen(
                        onNavigateToLogin = { currentScreen = ScreenState.Login },
                        onNavigateToRegister = { currentScreen = ScreenState.Register }
                    )
                }

                // --- 4. REGISTER ---
                ScreenState.Register -> {
                    RegistrationScreen(
                        onBack = { currentScreen = ScreenState.Welcome },
                        onRegisterSuccess = { name, email, pass ->
                            userManager.registerUser(name, email, pass)
                            currentScreen = ScreenState.MainApp
                        }
                    )
                }

                // --- 5. LOGIN ---
                ScreenState.Login -> {
                    LoginScreen(
                        onBack = { currentScreen = ScreenState.Welcome },
                        onLoginAttempt = { email, pass ->
                            if (userManager.loginUser(email, pass)) {
                                userManager.registerUser("User", email, pass) // Оновлюємо сесію
                                currentScreen = ScreenState.MainApp
                            } else {
                                Toast.makeText(applicationContext, "Email or password incorrect", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }

                // --- 6. MAIN APP (Головний екран) ---
                ScreenState.MainApp -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Hello!\nWelcome to Pet Care.",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3F51B5),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}