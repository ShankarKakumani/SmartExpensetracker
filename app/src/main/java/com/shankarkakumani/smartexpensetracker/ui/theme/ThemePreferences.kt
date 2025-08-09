// AI-generated: Theme preferences management for Smart Expense Tracker
package com.shankarkakumani.smartexpensetracker.ui.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Theme mode options for the app
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}

/**
 * Manages theme preferences and provides theme switching functionality
 */
class ThemePreferences(
    private val context: Context
) {
    companion object {
        private const val THEME_PREFS = "theme_preferences"
        private const val THEME_MODE_KEY = "theme_mode"
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE)
    
    private val _themeMode = MutableStateFlow(getCurrentThemeMode())
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()
    
    /**
     * Get the current theme mode from preferences
     */
    private fun getCurrentThemeMode(): ThemeMode {
        val savedMode = prefs.getString(THEME_MODE_KEY, ThemeMode.SYSTEM.name)
        return try {
            ThemeMode.valueOf(savedMode ?: ThemeMode.SYSTEM.name)
        } catch (e: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }
    
    /**
     * Update the theme mode and persist to preferences
     */
    fun updateThemeMode(themeMode: ThemeMode) {
        prefs.edit()
            .putString(THEME_MODE_KEY, themeMode.name)
            .apply()
        _themeMode.value = themeMode
    }
    
    /**
     * Toggle between light and dark theme
     */
    fun toggleTheme() {
        val newMode = when (_themeMode.value) {
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.SYSTEM -> ThemeMode.DARK // Default to dark when toggling from system
        }
        updateThemeMode(newMode)
    }
}

/**
 * Composable that provides the current theme mode state
 */
@Composable
fun rememberThemeMode(themePreferences: ThemePreferences): ThemeMode {
    val themeMode by themePreferences.themeMode.collectAsState()
    return themeMode
}
