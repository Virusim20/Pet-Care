package com.zabranska.petcare // Твій пакет

import android.content.Context

class UserManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Зберігаємо дані (Реєстрація)
    fun registerUser(fullName: String, email: String, pass: String) {
        prefs.edit().apply {
            putString("FULL_NAME", fullName)
            putString("EMAIL", email)
            putString("PASSWORD", pass)
            putBoolean("IS_LOGGED_IN", true) // Одразу логінимо
            apply()
        }
    }

    // Перевірка логіну (Вхід)
    fun loginUser(email: String, pass: String): Boolean {
        val savedEmail = prefs.getString("EMAIL", "")
        val savedPass = prefs.getString("PASSWORD", "")

        return email == savedEmail && pass == savedPass
    }

    // Чи користувач вже авторизований?
    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean("IS_LOGGED_IN", false)
    }

    // Вихід (для тестів, якщо знадобиться)
    fun logout() {
        prefs.edit().putBoolean("IS_LOGGED_IN", false).apply()
    }
}