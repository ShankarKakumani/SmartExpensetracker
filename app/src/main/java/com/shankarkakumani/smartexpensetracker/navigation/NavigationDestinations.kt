// AI-generated: Navigation destinations for Smart Expense Tracker
package com.shankarkakumani.smartexpensetracker.navigation

/**
 * Navigation destinations for the Smart Expense Tracker app.
 * Each destination represents a screen in the app with its route.
 */
sealed class NavigationDestinations(val route: String) {
    
    /**
     * Expense Entry screen - for adding new expenses
     */
    object ExpenseEntry : NavigationDestinations("expense_entry")
    
    /**
     * Expense List screen - for viewing and managing expenses
     */
    object ExpenseList : NavigationDestinations("expense_list")
    
    /**
     * Expense Report screen - for viewing analytics and reports
     */
    object ExpenseReport : NavigationDestinations("expense_report")
    
    companion object {
        /**
         * All available destinations in the app
         */
        val allDestinations = listOf(
            ExpenseEntry,
            ExpenseList,
            ExpenseReport
        )
        
        /**
         * Get destination by route string
         */
        fun fromRoute(route: String?): NavigationDestinations? {
            return allDestinations.find { it.route == route }
        }
    }
}

/**
 * Bottom navigation items for the main navigation
 */
data class BottomNavItem(
    val destination: NavigationDestinations,
    val title: String,
    val iconResourceName: String // We'll use Material Icons
)

/**
 * Bottom navigation configuration
 */
object BottomNavConfig {
    val items = listOf(
        BottomNavItem(
            destination = NavigationDestinations.ExpenseEntry,
            title = "Add Expense",
            iconResourceName = "add_circle"
        ),
        BottomNavItem(
            destination = NavigationDestinations.ExpenseList,
            title = "Expenses",
            iconResourceName = "list"
        ),
        BottomNavItem(
            destination = NavigationDestinations.ExpenseReport,
            title = "Reports",
            iconResourceName = "info"
        )
    )
}
