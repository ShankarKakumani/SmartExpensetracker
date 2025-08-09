// AI-generated: Navigation graph for Smart Expense Tracker
package com.shankarkakumani.smartexpensetracker.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.shankarkakumani.smartexpensetracker.presentation.expense_entry.ExpenseEntryScreen
import com.shankarkakumani.smartexpensetracker.presentation.expense_list.ExpenseListScreen
import com.shankarkakumani.smartexpensetracker.presentation.expense_report.ExpenseReportScreen

/**
 * Main navigation graph for the Smart Expense Tracker app.
 * Handles navigation between the three main screens with smooth animations.
 */
@Composable
fun SmartExpenseNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestinations.ExpenseEntry.route,
        modifier = modifier,
        enterTransition = {
            // Slide in from right with fade
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            // Slide out to left with fade
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            // Slide in from left when coming back
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            // Slide out to right when going back
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        // Expense Entry Screen
        composable(
            route = NavigationDestinations.ExpenseEntry.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = "smartexpense://entry" }
            )
        ) {
            ExpenseEntryScreen(
                onNavigateToExpenses = {
                    NavigationHelper.navigateTo(navController, NavigationDestinations.ExpenseList)
                },
                onNavigateToReports = {
                    NavigationHelper.navigateTo(navController, NavigationDestinations.ExpenseReport)
                }
            )
        }
        
        // Expense List Screen  
        composable(
            route = NavigationDestinations.ExpenseList.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = "smartexpense://list" },
                navDeepLink { uriPattern = "smartexpense://list?date={date}" }
            )
        ) {
            ExpenseListScreen(
                onNavigateToAddExpense = {
                    NavigationHelper.navigateTo(navController, NavigationDestinations.ExpenseEntry)
                }
            )
        }
        
        // Expense Report Screen
        composable(
            route = NavigationDestinations.ExpenseReport.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = "smartexpense://reports" },
                navDeepLink { uriPattern = "smartexpense://reports?period={period}" }
            )
        ) {
            ExpenseReportScreen(
                onNavigateBack = {
                    if (!navController.popBackStack()) {
                        NavigationHelper.navigateTo(navController, NavigationDestinations.ExpenseEntry)
                    }
                }
            )
        }
    }
}

/**
 * Navigation helper functions for common navigation actions
 */
object NavigationHelper {
    
    /**
     * Navigate to a destination and clear the back stack if needed
     */
    fun navigateTo(
        navController: NavHostController,
        destination: NavigationDestinations,
        clearBackStack: Boolean = false
    ) {
        if (clearBackStack) {
            navController.navigate(destination.route) {
                // Clear the back stack
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                // Restore state when reselecting a tab
                restoreState = true
            }
        } else {
            navController.navigate(destination.route) {
                // Avoid multiple instances of the same destination
                launchSingleTop = true
                // Restore state when reselecting a tab
                restoreState = true
            }
        }
    }
    
    /**
     * Navigate to expense list with optional pre-selected filters
     */
    fun navigateToExpenseList(
        navController: NavHostController,
        preSelectDate: String? = null
    ) {
        var route = NavigationDestinations.ExpenseList.route
        if (preSelectDate != null) {
            route += "?date=$preSelectDate"
        }
        
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }
    
    /**
     * Navigate to reports with optional pre-selected time period
     */
    fun navigateToReports(
        navController: NavHostController,
        timePeriod: String? = null
    ) {
        var route = NavigationDestinations.ExpenseReport.route
        if (timePeriod != null) {
            route += "?period=$timePeriod"
        }
        
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }
}
