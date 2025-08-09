// AI-generated: Repository interface for expense data operations
package com.shankarkakumani.domain.repository

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.domain.model.WeeklyReport
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for expense data operations.
 * This interface defines the contract for expense data access and follows Clean Architecture principles.
 * Implementation will be provided in the data layer.
 */
interface ExpenseRepository {
    
    /**
     * Adds a new expense to the repository.
     * @param expense The expense to add
     * @return Result containing the added expense with generated ID or error
     */
    suspend fun addExpense(expense: Expense): Result<Expense>
    
    /**
     * Retrieves all expenses as a Flow for reactive updates.
     * @return Flow of Result containing list of expenses or error
     */
    fun getAllExpenses(): Flow<Result<List<Expense>>>
    
    /**
     * Retrieves expenses for a specific date range.
     * @param startDate Start timestamp (inclusive)
     * @param endDate End timestamp (inclusive)
     * @return Result containing filtered expenses or error
     */
    suspend fun getExpensesByDateRange(startDate: Long, endDate: Long): Result<List<Expense>>
    
    /**
     * Retrieves expenses for a specific category.
     * @param category The expense category to filter by
     * @return Result containing filtered expenses or error
     */
    suspend fun getExpensesByCategory(category: ExpenseCategory): Result<List<Expense>>
    
    /**
     * Retrieves expenses filtered by both date range and category.
     * @param startDate Start timestamp (inclusive)
     * @param endDate End timestamp (inclusive)
     * @param category The expense category to filter by
     * @return Result containing filtered expenses or error
     */
    suspend fun getExpensesByDateRangeAndCategory(
        startDate: Long, 
        endDate: Long, 
        category: ExpenseCategory
    ): Result<List<Expense>>
    
    /**
     * Gets the total amount spent today.
     * @return Result containing today's total amount or error
     */
    suspend fun getTotalSpentToday(): Result<Double>
    
    /**
     * Gets the total amount spent for a specific date.
     * @param date The date timestamp
     * @return Result containing the total amount for that date or error
     */
    suspend fun getTotalSpentOnDate(date: Long): Result<Double>
    
    /**
     * Generates a weekly report for the last 7 days.
     * @return Result containing weekly report data or error
     */
    suspend fun getWeeklyReport(): Result<WeeklyReport>
    
    /**
     * Gets the count of expenses for today.
     * @return Result containing today's expense count or error
     */
    suspend fun getExpenseCountToday(): Result<Int>
    
    /**
     * Gets the count of expenses for a specific date range.
     * @param startDate Start timestamp (inclusive)
     * @param endDate End timestamp (inclusive)
     * @return Result containing expense count for the range or error
     */
    suspend fun getExpenseCountByDateRange(startDate: Long, endDate: Long): Result<Int>
    
    /**
     * Updates an existing expense.
     * @param expense The expense to update
     * @return Result containing the updated expense or error
     */
    suspend fun updateExpense(expense: Expense): Result<Expense>
    
    /**
     * Deletes an expense by ID.
     * @param expenseId The ID of the expense to delete
     * @return Result indicating success or error
     */
    suspend fun deleteExpense(expenseId: String): Result<Unit>
    
    /**
     * Searches expenses by title or notes.
     * @param query The search query
     * @return Result containing matching expenses or error
     */
    suspend fun searchExpenses(query: String): Result<List<Expense>>
    
    /**
     * Gets expenses grouped by category for analytics.
     * @param startDate Start timestamp (inclusive)
     * @param endDate End timestamp (inclusive)
     * @return Result containing category-grouped expenses or error
     */
    suspend fun getExpensesGroupedByCategory(
        startDate: Long, 
        endDate: Long
    ): Result<Map<ExpenseCategory, List<Expense>>>
}
