package com.zabranska.petcare // Перевір пакет!

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

// Enum для навігації (найпростіший варіант)
enum class ScreenState {
    Splash, Welcome, Login, Register, MainApp
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Ініціалізуємо менеджер даних
        val userManager = UserManager(this)

        setContent {
            // Зберігаємо поточний стан екрану
            var currentScreen by remember { mutableStateOf(ScreenState.Splash) }

            // Логіка перемикання екранів
            when (currentScreen) {
                ScreenState.Splash -> {
                    WelcomeScreen(onTimeout = {
                        // Якщо користувач вже залогінений -> одразу в додаток
                        if (userManager.isUserLoggedIn()) {
                            currentScreen = ScreenState.MainApp
                        } else {
                            currentScreen = ScreenState.Welcome
                        }
                    })
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
                            // Зберігаємо в SharedPrefs
                            userManager.registerUser(name, email, pass)
                            currentScreen = ScreenState.MainApp
                        }
                    )
                }
                ScreenState.Login -> {
                    LoginScreen(
                        onBack = { currentScreen = ScreenState.Welcome },
                        onLoginAttempt = { email, pass ->
                            // Перевіряємо дані
                            if (userManager.loginUser(email, pass)) {
                                // Успіх -> зберігаємо статус і йдемо далі
                                userManager.registerUser("User", email, pass) // Оновлюємо сесію
                                currentScreen = ScreenState.MainApp
                            } else {
                                Toast.makeText(applicationContext, "Email or password incorrect", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
                ScreenState.MainApp -> {
                    // Твій головний екран
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("MAIN APP SCREEN \nUser Authorized!")
                    }
                }
            }
        }
    }
}