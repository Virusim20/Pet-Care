package com.zabranska.petcare // Залиш свій пакет

import android.content.Context

class UserManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // --- Реєстрація та Вхід ---
    fun registerUser(fullName: String, email: String, pass: String) {
        prefs.edit().apply {
            putString("FULL_NAME", fullName)
            putString("EMAIL", email)
            putString("PASSWORD", pass)
            putBoolean("IS_LOGGED_IN", true)
            apply()
        }
    }

    fun loginUser(email: String, pass: String): Boolean {
        val savedEmail = prefs.getString("EMAIL", "")
        val savedPass = prefs.getString("PASSWORD", "")
        return email == savedEmail && pass == savedPass
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean("IS_LOGGED_IN", false)
    }

    fun logout() {
        prefs.edit().putBoolean("IS_LOGGED_IN", false).apply()
    }

    // --- Онбордінг (Нові методи) ---

    // Зберігаємо, що користувач пройшов навчання
    fun saveOnboardingFinished() {
        prefs.edit().putBoolean("IS_ONBOARDING_FINISHED", true).apply()
    }

    // Перевіряємо, чи треба показувати навчання
    fun isOnboardingFinished(): Boolean {
        return prefs.getBoolean("IS_ONBOARDING_FINISHED", false)
    }
}