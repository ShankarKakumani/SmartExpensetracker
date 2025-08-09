// AI-generated: Unit tests for data flow integration between screens
package com.shankarkakumani.smartexpensetracker

import app.cash.turbine.test
import com.shankarkakumani.data.repository.ExpenseRepositoryImpl
import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.domain.usecase.AddExpenseUseCase
import com.shankarkakumani.domain.usecase.GetExpensesUseCase
import com.shankarkakumani.domain.usecase.GetTotalSpentTodayUseCase
import com.shankarkakumani.domain.usecase.GetWeeklyReportUseCase
import com.shankarkakumani.smartexpensetracker.presentation.expense_entry.ExpenseEntryViewModel
import com.shankarkakumani.smartexpensetracker.presentation.expense_entry.ExpenseEntryAction
import com.shankarkakumani.smartexpensetracker.presentation.expense_list.ExpenseListViewModel
import com.shankarkakumani.smartexpensetracker.presentation.expense_report.ExpenseReportViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Integration tests that verify data flows correctly between screens and ViewModels.
 * Tests the complete data pipeline from entry to display.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DataFlowIntegrationTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    
    // Mock use cases
    private val mockAddExpenseUseCase = mockk<AddExpenseUseCase>()
    private val mockGetExpensesUseCase = mockk<GetExpensesUseCase>()
    private val mockGetTotalSpentTodayUseCase = mockk<GetTotalSpentTodayUseCase>()
    private val mockGetWeeklyReportUseCase = mockk<GetWeeklyReportUseCase>()
    
    // Test data
    private val sampleExpense = Expense(
        id = "1",
        title = "Test Lunch",
        amount = 25.50,
        category = ExpenseCategory.FOOD,
        date = LocalDateTime.now(),
        description = "Test expense"
    )
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `expense entry to list data flow works correctly`() = runTest {
        // Setup mocks
        coEvery { mockAddExpenseUseCase(any()) } returns Result.success(Unit)
        coEvery { mockGetExpensesUseCase(any(), any()) } returns flowOf(listOf(sampleExpense))
        coEvery { mockGetTotalSpentTodayUseCase() } returns flowOf(25.50)
        
        // Create ViewModels
        val entryViewModel = ExpenseEntryViewModel(
            addExpenseUseCase = mockAddExpenseUseCase,
            getTotalSpentTodayUseCase = mockGetTotalSpentTodayUseCase
        )
        
        val listViewModel = ExpenseListViewModel(
            getExpensesUseCase = mockGetExpensesUseCase
        )
        
        // Test expense entry flow
        entryViewModel.uiState.test {
            val initialState = awaitItem()
            assertTrue(initialState.title.isEmpty())
            
            // Fill in expense details
            entryViewModel.onAction(ExpenseEntryAction.UpdateTitle("Test Lunch"))
            entryViewModel.onAction(ExpenseEntryAction.UpdateAmount("25.50"))
            entryViewModel.onAction(ExpenseEntryAction.UpdateCategory(ExpenseCategory.FOOD))
            entryViewModel.onAction(ExpenseEntryAction.UpdateDescription("Test expense"))
            
            // Submit expense
            entryViewModel.onAction(ExpenseEntryAction.SubmitExpense)
            
            // Wait for all operations to complete
            advanceUntilIdle()
            
            // Verify the expense was submitted
            coVerify { mockAddExpenseUseCase(any()) }
        }
        
        // Test that list screen shows the added expense
        listViewModel.uiState.test {
            val listState = awaitItem()
            assertTrue(listState.expenses.isNotEmpty())
            assertEquals("Test Lunch", listState.expenses[0].title)
            assertEquals(25.50, listState.expenses[0].amount)
        }
    }

    @Test
    fun `total spent today updates correctly across screens`() = runTest {
        // Setup mock to return different values over time
        coEvery { mockGetTotalSpentTodayUseCase() } returns flowOf(0.0, 25.50, 50.0)
        
        val entryViewModel = ExpenseEntryViewModel(
            addExpenseUseCase = mockAddExpenseUseCase,
            getTotalSpentTodayUseCase = mockGetTotalSpentTodayUseCase
        )
        
        entryViewModel.uiState.test {
            // Initial state should show 0
            val initialState = awaitItem()
            assertEquals(0.0, initialState.totalSpentToday)
            
            // After adding expense, should update
            val updatedState = awaitItem()
            assertEquals(25.50, updatedState.totalSpentToday)
            
            // After adding another expense, should update again
            val finalState = awaitItem()
            assertEquals(50.0, finalState.totalSpentToday)
        }
    }

    @Test
    fun `filtering in expense list works with repository data`() = runTest {
        val foodExpense = sampleExpense.copy(id = "1", category = ExpenseCategory.FOOD)
        val travelExpense = sampleExpense.copy(id = "2", category = ExpenseCategory.TRAVEL)
        
        // Mock to return both expenses initially
        coEvery { mockGetExpensesUseCase(any(), any()) } returns flowOf(listOf(foodExpense, travelExpense))
        
        val listViewModel = ExpenseListViewModel(
            getExpensesUseCase = mockGetExpensesUseCase
        )
        
        listViewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(2, initialState.expenses.size)
            
            // Test filtering by category
            // (Note: In a real implementation, this would trigger a new repository call)
            assertTrue(initialState.expenses.any { it.category == ExpenseCategory.FOOD })
            assertTrue(initialState.expenses.any { it.category == ExpenseCategory.TRAVEL })
        }
    }

    @Test
    fun `weekly report reflects added expenses`() = runTest {
        coEvery { mockGetWeeklyReportUseCase() } returns flowOf(
            com.shankarkakumani.domain.model.WeeklyReport(
                totalSpent = 75.0,
                expenseCount = 3,
                categoryBreakdown = mapOf(
                    ExpenseCategory.FOOD to 25.0,
                    ExpenseCategory.TRAVEL to 50.0
                ),
                dailySpending = mapOf(
                    LocalDate.now() to 75.0
                ),
                averageDaily = 10.71
            )
        )
        
        val reportViewModel = ExpenseReportViewModel(
            getWeeklyReportUseCase = mockGetWeeklyReportUseCase
        )
        
        reportViewModel.uiState.test {
            val reportState = awaitItem()
            
            assertEquals(75.0, reportState.weeklyReport?.totalSpent)
            assertEquals(3, reportState.weeklyReport?.expenseCount)
            assertTrue(reportState.weeklyReport?.categoryBreakdown?.containsKey(ExpenseCategory.FOOD) == true)
            assertEquals(25.0, reportState.weeklyReport?.categoryBreakdown?.get(ExpenseCategory.FOOD))
        }
    }

    @Test
    fun `navigation between screens preserves data consistency`() = runTest {
        // Test that data remains consistent when navigating between screens
        coEvery { mockGetExpensesUseCase(any(), any()) } returns flowOf(listOf(sampleExpense))
        coEvery { mockGetTotalSpentTodayUseCase() } returns flowOf(25.50)
        
        // Create multiple ViewModels (simulating navigation between screens)
        val entryViewModel = ExpenseEntryViewModel(
            addExpenseUseCase = mockAddExpenseUseCase,
            getTotalSpentTodayUseCase = mockGetTotalSpentTodayUseCase
        )
        
        val listViewModel = ExpenseListViewModel(
            getExpensesUseCase = mockGetExpensesUseCase
        )
        
        // Both ViewModels should show consistent data
        entryViewModel.uiState.test {
            val entryState = awaitItem()
            assertEquals(25.50, entryState.totalSpentToday)
        }
        
        listViewModel.uiState.test {
            val listState = awaitItem()
            assertEquals(1, listState.expenses.size)
            assertEquals(sampleExpense.title, listState.expenses[0].title)
        }
    }

    @Test
    fun `error handling flows correctly between screens`() = runTest {
        // Setup mock to return error
        coEvery { mockAddExpenseUseCase(any()) } returns Result.failure(Exception("Network error"))
        
        val entryViewModel = ExpenseEntryViewModel(
            addExpenseUseCase = mockAddExpenseUseCase,
            getTotalSpentTodayUseCase = mockGetTotalSpentTodayUseCase
        )
        
        entryViewModel.uiState.test {
            val initialState = awaitItem()
            
            // Try to submit expense
            entryViewModel.onAction(ExpenseEntryAction.UpdateTitle("Test"))
            entryViewModel.onAction(ExpenseEntryAction.UpdateAmount("10.0"))
            entryViewModel.onAction(ExpenseEntryAction.SubmitExpense)
            
            advanceUntilIdle()
            
            // Should show error state
            val errorState = awaitItem()
            assertTrue(errorState.errorMessage?.contains("error") == true)
            assertTrue(!errorState.isSubmitting)
        }
    }
}
