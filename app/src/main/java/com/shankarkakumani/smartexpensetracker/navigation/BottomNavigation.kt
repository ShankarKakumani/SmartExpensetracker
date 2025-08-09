// AI-generated: Bottom navigation component for Smart Expense Tracker
package com.shankarkakumani.smartexpensetracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Bottom navigation bar for the Smart Expense Tracker app.
 * Provides navigation between the three main screens.
 */
@Composable
fun SmartExpenseBottomNavigation(
    navController: NavController,
    onNavigate: (NavigationDestinations) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = BottomNavigationDefaults.Elevation
    ) {
        BottomNavConfig.items.forEach { item ->
            val isSelected = currentRoute == item.destination.route
            
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.destination) },
                icon = {
                    Icon(
                        imageVector = getIconForDestination(item.destination),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.semantics { contentDescription = "BottomNav: ${item.title}" }
            )
        }
    }
}

/**
 * Get the appropriate icon for each navigation destination
 */
private fun getIconForDestination(destination: NavigationDestinations): ImageVector {
    return when (destination) {
        NavigationDestinations.ExpenseEntry -> Icons.Filled.Add
        NavigationDestinations.ExpenseList -> Icons.Filled.List
        NavigationDestinations.ExpenseReport -> Icons.Filled.Info
    }
}

/**
 * Default values for bottom navigation
 */
private object BottomNavigationDefaults {
    val Elevation = 8.dp
}
