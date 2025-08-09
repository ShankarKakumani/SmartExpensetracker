// AI-generated: Main Activity for Smart Expense Tracker with Hilt integration and Navigation
package com.shankarkakumani.smartexpensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.shankarkakumani.smartexpensetracker.navigation.NavigationDestinations
import com.shankarkakumani.smartexpensetracker.navigation.NavigationHelper
import com.shankarkakumani.smartexpensetracker.navigation.SmartExpenseBottomNavigation
import com.shankarkakumani.smartexpensetracker.navigation.SmartExpenseNavigation
import com.shankarkakumani.smartexpensetracker.ui.components.SimpleThemeToggle
import com.shankarkakumani.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme
import com.shankarkakumani.smartexpensetracker.ui.theme.ThemeMode
import com.shankarkakumani.smartexpensetracker.ui.theme.ThemePreferences
import com.shankarkakumani.smartexpensetracker.ui.theme.rememberThemeMode
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // AI-generated: Inject ThemePreferences for theme persistence and switching
    @Inject
    lateinit var themePreferences: ThemePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartExpenseTrackerApp(themePreferences)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartExpenseTrackerApp(
    themePreferences: ThemePreferences? = null
) {
    // AI-generated: Observe theme mode from preferences and apply theme
    val context = LocalContext.current
    val provided = remember(themePreferences, context) {
        themePreferences ?: ThemePreferences(context)
    }
    val themeMode = rememberThemeMode(provided)
    val isDarkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
    }

    SmartExpenseTrackerTheme(darkTheme = isDarkTheme) {
        SmartExpenseTrackerContent(
            currentThemeMode = themeMode,
            onThemeChange = { provided.updateThemeMode(it) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartExpenseTrackerContent(
    currentThemeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Get the screen title based on current route
    val screenTitle = when (currentRoute) {
        NavigationDestinations.ExpenseEntry.route -> "Add Expense"
        NavigationDestinations.ExpenseList.route -> "My Expenses"
        NavigationDestinations.ExpenseReport.route -> "Reports"
        else -> "Smart Expense Tracker"
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = screenTitle,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    SimpleThemeToggle(
                        isDarkTheme = currentThemeMode == ThemeMode.DARK,
                        onToggle = { onThemeChange(
                            if (currentThemeMode == ThemeMode.DARK) ThemeMode.LIGHT else ThemeMode.DARK
                        ) }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            SmartExpenseBottomNavigation(
                navController = navController,
                onNavigate = { destination ->
                    NavigationHelper.navigateTo(navController, destination)
                }
            )
        }
    ) { innerPadding ->
        SmartExpenseNavigation(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmartExpenseTrackerAppPreview() {
    SmartExpenseTrackerTheme {
        SmartExpenseTrackerApp()
    }
}