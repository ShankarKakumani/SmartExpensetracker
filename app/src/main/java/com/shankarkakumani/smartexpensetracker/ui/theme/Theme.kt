// AI-generated: Enhanced Material 3 theme with custom color schemes and theme switching
package com.shankarkakumani.smartexpensetracker.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Enhanced Dark Color Scheme for Expense Tracker
private val ExpenseDarkColorScheme = darkColorScheme(
    primary = ExpenseGreen80,
    onPrimary = ExpenseGreen20,
    primaryContainer = ExpenseGreen40,
    onPrimaryContainer = ExpenseGreen80,
    
    secondary = ExpenseBlue80,
    onSecondary = ExpenseBlue20,
    secondaryContainer = ExpenseBlue40,
    onSecondaryContainer = ExpenseBlue80,
    
    tertiary = ExpenseOrange80,
    onTertiary = ExpenseOrange20,
    tertiaryContainer = ExpenseOrange40,
    onTertiaryContainer = ExpenseOrange80,
    
    error = ExpenseRed80,
    onError = ExpenseRed20,
    errorContainer = ExpenseRed40,
    onErrorContainer = ExpenseRed80,
    
    background = BackgroundDark,
    onBackground = OnSurfaceDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    scrim = Color.Black,
    
    inverseSurface = SurfaceLight,
    inverseOnSurface = OnSurfaceLight,
    inversePrimary = ExpenseGreen40
)

// Enhanced Light Color Scheme for Expense Tracker
private val ExpenseLightColorScheme = lightColorScheme(
    primary = ExpenseGreen60,
    onPrimary = Color.White,
    primaryContainer = ExpenseGreen80,
    onPrimaryContainer = ExpenseGreen20,
    
    secondary = ExpenseBlue60,
    onSecondary = Color.White,
    secondaryContainer = ExpenseBlue80,
    onSecondaryContainer = ExpenseBlue20,
    
    tertiary = ExpenseOrange60,
    onTertiary = Color.White,
    tertiaryContainer = ExpenseOrange80,
    onTertiaryContainer = ExpenseOrange20,
    
    error = ExpenseRed60,
    onError = Color.White,
    errorContainer = ExpenseRed80,
    onErrorContainer = ExpenseRed20,
    
    background = BackgroundLight,
    onBackground = OnSurfaceLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    scrim = Color.Black,
    
    inverseSurface = SurfaceDark,
    inverseOnSurface = OnSurfaceDark,
    inversePrimary = ExpenseGreen80
)

@Composable
fun SmartExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled to use custom colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> ExpenseDarkColorScheme
        else -> ExpenseLightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}