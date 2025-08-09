// AI-generated: Integration tests for navigation flows between screens
package com.shankarkakumani.smartexpensetracker

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shankarkakumani.smartexpensetracker.navigation.NavigationDestinations
import com.shankarkakumani.smartexpensetracker.navigation.SmartExpenseNavigation
import com.shankarkakumani.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Integration tests for navigation between screens in the Smart Expense Tracker app.
 * Tests navigation flows, deep linking, and data consistency across screens.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        hiltRule.inject()
        
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            
            SmartExpenseTrackerTheme {
                SmartExpenseNavigation(navController = navController)
            }
        }
    }

    @Test
    fun navigationStartsAtExpenseEntryScreen() {
        // Verify we start at the expense entry screen
        composeTestRule.onNodeWithText("Add Expense").assertIsDisplayed()
        
        // Verify the route is correct
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseEntry.route)
    }

    @Test
    fun canNavigateToAllScreensViaBottomNav() {
        // Start at expense entry
        composeTestRule.onNodeWithText("Add Expense").assertIsDisplayed()
        
        // Navigate to expense list via bottom navigation
        composeTestRule.onNodeWithContentDescription("Expenses").performClick()
        composeTestRule.waitForIdle()
        
        // Verify we're at expense list
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseList.route)
        
        // Navigate to reports via bottom navigation
        composeTestRule.onNodeWithContentDescription("Reports").performClick()
        composeTestRule.waitForIdle()
        
        // Verify we're at reports
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseReport.route)
        
        // Navigate back to expense entry
        composeTestRule.onNodeWithContentDescription("Add Expense").performClick()
        composeTestRule.waitForIdle()
        
        // Verify we're back at expense entry
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseEntry.route)
    }

    @Test
    fun expenseEntryToListNavigationFlow() {
        // Start at expense entry screen
        composeTestRule.onNodeWithText("Add Expense").assertIsDisplayed()
        
        // Fill in a sample expense (basic test)
        composeTestRule.onNodeWithText("Expense Title").assertIsDisplayed()
        
        // Note: This test focuses on navigation structure rather than full form submission
        // which would require more complex setup with test data
        
        // Navigate to list manually to test navigation works
        composeTestRule.onNodeWithContentDescription("Expenses").performClick()
        composeTestRule.waitForIdle()
        
        // Verify navigation occurred
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseList.route)
    }

    @Test
    fun backNavigationBehavior() {
        // Navigate to expense list
        composeTestRule.onNodeWithContentDescription("Expenses").performClick()
        composeTestRule.waitForIdle()
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseList.route)
        
        // Navigate to reports
        composeTestRule.onNodeWithContentDescription("Reports").performClick()
        composeTestRule.waitForIdle()
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseReport.route)
        
        // Test back navigation
        navController.popBackStack()
        composeTestRule.waitForIdle()
        
        // Should be back at the previous screen
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseList.route)
    }

    @Test
    fun deepLinkNavigationToExpenseList() {
        // Test deep link navigation
        navController.navigate("smartexpense://list")
        composeTestRule.waitForIdle()
        
        // Verify we navigated to expense list via deep link
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseList.route)
    }

    @Test
    fun deepLinkNavigationToReports() {
        // Test deep link navigation
        navController.navigate("smartexpense://reports")
        composeTestRule.waitForIdle()
        
        // Verify we navigated to reports via deep link
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseReport.route)
    }

    @Test
    fun navigationStatePreservation() {
        // Navigate through multiple screens
        composeTestRule.onNodeWithContentDescription("Expenses").performClick()
        composeTestRule.waitForIdle()
        
        composeTestRule.onNodeWithContentDescription("Reports").performClick()
        composeTestRule.waitForIdle()
        
        // Navigate back to entry
        composeTestRule.onNodeWithContentDescription("Add Expense").performClick()
        composeTestRule.waitForIdle()
        
        // Verify we can still navigate normally (state is preserved)
        composeTestRule.onNodeWithContentDescription("Expenses").performClick()
        composeTestRule.waitForIdle()
        
        assert(navController.currentBackStackEntry?.destination?.route == NavigationDestinations.ExpenseList.route)
    }
}
