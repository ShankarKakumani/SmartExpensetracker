// AI-generated: Use case for retrieving expenses with filtering options
package com.shankarkakumani.domain.usecase

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.domain.repository.ExpenseRepository
import com.shankarkakumani.domain.util.DateUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving expenses with various filtering options.
 * Provides flexible access to expense data with business logic for filtering.
 */
class GetExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    
    /**
     * Gets all expenses as a reactive Flow.
     * @return Flow of Result containing list of expenses
     */
    fun getAllExpenses(): Flow<Result<List<Expense>>> {
        return expenseRepository.getAllExpenses()
    }
    
    /**
     * Gets expenses for today only.
     * @return Result containing today's expenses
     */
    suspend fun getTodayExpenses(): Result<List<Expense>> {
        val startOfToday = DateUtils.getStartOfToday()
        val endOfToday = DateUtils.getEndOfToday()
        return expenseRepository.getExpensesByDateRange(startOfToday, endOfToday)
    }
    
    /**
     * Gets expenses for a specific date.
     * @param date The target date timestamp
     * @return Result containing expenses for the specified date
     */
    suspend fun getExpensesForDate(date: Long): Result<List<Expense>> {
        val startOfDate = DateUtils.getStartOfDate(date)
        val endOfDate = DateUtils.getEndOfDate(date)
        return expenseRepository.getExpensesByDateRange(startOfDate, endOfDate)
    }
    
    /**
     * Gets expenses for a date range.
     * @param startDate Start date timestamp
     * @param endDate End date timestamp
     * @return Result containing expenses in the specified range
     */
    suspend fun getExpensesByDateRange(startDate: Long, endDate: Long): Result<List<Expense>> {
        return expenseRepository.getExpensesByDateRange(startDate, endDate)
    }
    
    /**
     * Gets expenses for a specific category.
     * @param category The expense category to filter by
     * @return Result containing expenses of the specified category
     */
    suspend fun getExpensesByCategory(category: ExpenseCategory): Result<List<Expense>> {
        return expenseRepository.getExpensesByCategory(category)
    }
    
    /**
     * Gets expenses filtered by category and date range.
     * @param category The expense category to filter by
     * @param startDate Start date timestamp
     * @param endDate End date timestamp
     * @return Result containing filtered expenses
     */
    suspend fun getExpensesByCategoryAndDateRange(
        category: ExpenseCategory,
        startDate: Long,
        endDate: Long
    ): Result<List<Expense>> {
        return expenseRepository.getExpensesByDateRangeAndCategory(startDate, endDate, category)
    }
    
    /**
     * Gets expenses for the current week.
     * @return Result containing this week's expenses
     */
    suspend fun getThisWeekExpenses(): Result<List<Expense>> {
        val startOfWeek = DateUtils.getStartOfWeek()
        val endOfWeek = DateUtils.getEndOfWeek()
        return expenseRepository.getExpensesByDateRange(startOfWeek, endOfWeek)
    }
    
    /**
     * Searches expenses by title or notes.
     * @param query The search query
     * @return Result containing matching expenses
     */
    suspend fun searchExpenses(query: String): Result<List<Expense>> {
        return if (query.isBlank()) {
            Result.Success(emptyList())
        } else {
            expenseRepository.searchExpenses(query.trim())
        }
    }
    
    /**
     * Gets expenses grouped by category for a date range.
     * Useful for analytics and reports.
     * @param startDate Start date timestamp
     * @param endDate End date timestamp
     * @return Result containing category-grouped expenses
     */
    suspend fun getExpensesGroupedByCategory(
        startDate: Long,
        endDate: Long
    ): Result<Map<ExpenseCategory, List<Expense>>> {
        return expenseRepository.getExpensesGroupedByCategory(startDate, endDate)
    }
    
    /**
     * Filter options for expense retrieval.
     */
    data class FilterOptions(
        val startDate: Long? = null,
        val endDate: Long? = null,
        val category: ExpenseCategory? = null,
        val searchQuery: String? = null
    )
    
    /**
     * Gets expenses with multiple filter options applied.
     * @param filterOptions The filter criteria
     * @return Result containing filtered expenses
     */
    suspend fun getExpensesWithFilters(filterOptions: FilterOptions): Result<List<Expense>> {
        return try {
            // Start with all expenses or apply basic filters
            val baseResult = when {
                filterOptions.startDate != null && filterOptions.endDate != null -> {
                    if (filterOptions.category != null) {
                        expenseRepository.getExpensesByDateRangeAndCategory(
                            filterOptions.startDate,
                            filterOptions.endDate,
                            filterOptions.category
                        )
                    } else {
                        expenseRepository.getExpensesByDateRange(
                            filterOptions.startDate,
                            filterOptions.endDate
                        )
                    }
                }
                filterOptions.category != null -> {
                    expenseRepository.getExpensesByCategory(filterOptions.category)
                }
                else -> {
                    expenseRepository.getExpensesByDateRange(0, Long.MAX_VALUE) // All expenses
                }
            }
            
            // Apply search filter if provided
            if (baseResult is Result.Success && !filterOptions.searchQuery.isNullOrBlank()) {
                val filteredExpenses = baseResult.data.filter { expense ->
                    expense.title.contains(filterOptions.searchQuery, ignoreCase = true) ||
                    expense.notes?.contains(filterOptions.searchQuery, ignoreCase = true) == true
                }
                Result.Success(filteredExpenses)
            } else {
                baseResult
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
