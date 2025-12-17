package com.zabranska.petcare // Перевір, щоб це був твій пакет

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.* import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Робить на весь екран

        setContent {
            // Стан перемикання екранів
            var splashFinished by remember { mutableStateOf(false) }

            if (!splashFinished) {
                // Викликаємо функцію з ІНШОГО файлу (WelcomeScreen.kt)
                WelcomeScreen(
                    onTimeout = {
                        splashFinished = true
                    }
                )
            } else {
                // Головний екран після завантаження
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Main App Screen")
                }
            }
        }
    }
}