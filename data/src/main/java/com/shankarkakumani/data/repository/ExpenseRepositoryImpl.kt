// AI-generated: Repository implementation for expense data operations
package com.shankarkakumani.data.repository

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.data.datasource.ExpenseLocalDataSource
import com.shankarkakumani.data.mapper.ExpenseMapper
import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.domain.model.WeeklyReport
import com.shankarkakumani.domain.model.DayTotal
import com.shankarkakumani.domain.model.CategorySummary
import com.shankarkakumani.domain.repository.ExpenseRepository
import com.shankarkakumani.domain.util.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of ExpenseRepository interface.
 * Handles data access, error handling, and mapping between domain and data layers.
 */
@Singleton
class ExpenseRepositoryImpl @Inject constructor(
    private val dataSource: ExpenseLocalDataSource,
    private val mapper: ExpenseMapper
) : ExpenseRepository {
    
    override suspend fun addExpense(expense: Expense): Result<Expense> {
        return try {
            val dto = mapper.toDto(expense)
            val savedDto = dataSource.addExpense(dto)
            val savedExpense = mapper.toDomain(savedDto)
            Result.Success(savedExpense)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override fun getAllExpenses(): Flow<Result<List<Expense>>> {
        return dataSource.expensesFlow
            .map { dtos ->
                try {
                    val expenses = mapper.toDomainList(dtos)
                    Result.Success(expenses)
                } catch (e: Exception) {
                    Result.Error(e)
                }
            }
            .catch { e ->
                emit(Result.Error(e))
            }
    }
    
    override suspend fun getExpensesByDateRange(startDate: Long, endDate: Long): Result<List<Expense>> {
        return try {
            val dtos = dataSource.getExpensesByDateRange(startDate, endDate)
            val expenses = mapper.toDomainList(dtos)
            Result.Success(expenses)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun getExpensesByCategory(category: ExpenseCategory): Result<List<Expense>> {
        return try {
            val dtos = dataSource.getExpensesByCategory(category.name)
            val expenses = mapper.toDomainList(dtos)
            Result.Success(expenses)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun getExpensesByDateRangeAndCategory(
        startDate: Long,
        endDate: Long,
        category: ExpenseCategory
    ): Result<List<Expense>> {
        return try {
            val dtos = dataSource.getExpensesByDateRangeAndCategory(startDate, endDate, category.name)
            val expenses = mapper.toDomainList(dtos)
            Result.Success(expenses)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun getTotalSpentToday(): Result<Double> {
        return try {
            val startOfToday = DateUtils.getStartOfToday()
            val endOfToday = DateUtils.getEndOfToday()
            val dtos = dataSource.getExpensesByDateRange(startOfToday, endOfToday)
            val total = dtos.sumOf { it.amount }
            Result.Success(total)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun getTotalSpentOnDate(date: Long): Result<Double> {
        return try {
            val startOfDate = DateUtils.getStartOfDate(date)
            val endOfDate = DateUtils.getEndOfDate(date)
            val dtos = dataSource.getExpensesByDateRange(startOfDate, endOfDate)
            val total = dtos.sumOf { it.amount }
            Result.Success(total)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun getWeeklyReport(): Result<WeeklyReport> {
        return try {
            val weekRanges = DateUtils.getLast7DaysRange()
            val weekStart = weekRanges.first().first
            val weekEnd = weekRanges.last().second
            
            // Get all expenses for the week
            val weeklyDtos = dataSource.getExpensesByDateRange(weekStart, weekEnd)
            val weeklyExpenses = mapper.toDomainList(weeklyDtos)
            
            // Calculate daily totals
            val dailyTotals = weekRanges.map { (dayStart, dayEnd) ->
                val dayExpenses = weeklyExpenses.filter { expense ->
                    expense.timestamp in dayStart..dayEnd
                }
                DayTotal(
                    date = dayStart,
                    amount = dayExpenses.sumOf { it.amount },
                    expenseCount = dayExpenses.size
                )
            }
            
            // Calculate category breakdown
            val categoryBreakdown = ExpenseCategory.values().associateWith { category ->
                val categoryExpenses = weeklyExpenses.filter { it.category == category }
                CategorySummary(
                    category = category,
                    totalAmount = categoryExpenses.sumOf { it.amount },
                    expenseCount = categoryExpenses.size,
                    averageAmount = if (categoryExpenses.isNotEmpty()) {
                        categoryExpenses.sumOf { it.amount } / categoryExpenses.size
                    } else 0.0
                )
            }
            
            val totalAmount = weeklyExpenses.sumOf { it.amount }
            val totalExpenses = weeklyExpenses.size
            val averageDailySpending = totalAmount / 7.0 // 7 days
            
            val report = WeeklyReport(
                weekStartDate = weekStart,
                weekEndDate = weekEnd,
                totalAmount = totalAmount,
                totalExpenses = totalExpenses,
                dailyTotals = dailyTotals,
                categoryBreakdown = categoryBreakdown,
                averageDailySpending = averageDailySpending
            )
            
            Result.Success(report)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun getExpenseCountToday(): Result<Int> {
        return try {
            val startOfToday = DateUtils.getStartOfToday()
            val endOfToday = DateUtils.getEndOfToday()
            val count = dataSource.getExpenseCountByDateRange(startOfToday, endOfToday)
            Result.Success(count)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun getExpenseCountByDateRange(startDate: Long, endDate: Long): Result<Int> {
        return try {
            val count = dataSource.getExpenseCountByDateRange(startDate, endDate)
            Result.Success(count)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun updateExpense(expense: Expense): Result<Expense> {
        return try {
            val dto = mapper.toDto(expense)
            val updatedDto = dataSource.updateExpense(dto)
            if (updatedDto != null) {
                val updatedExpense = mapper.toDomain(updatedDto)
                Result.Success(updatedExpense)
            } else {
                Result.Error(NoSuchElementException("Expense with id ${expense.id} not found"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun deleteExpense(expenseId: String): Result<Unit> {
        return try {
            val deleted = dataSource.deleteExpense(expenseId)
            if (deleted) {
                Result.Success(Unit)
            } else {
                Result.Error(NoSuchElementException("Expense with id $expenseId not found"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun searchExpenses(query: String): Result<List<Expense>> {
        return try {
            val dtos = dataSource.searchExpenses(query)
            val expenses = mapper.toDomainList(dtos)
            Result.Success(expenses)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun getExpensesGroupedByCategory(
        startDate: Long,
        endDate: Long
    ): Result<Map<ExpenseCategory, List<Expense>>> {
        return try {
            val dtos = dataSource.getExpensesByDateRange(startDate, endDate)
            val expenses = mapper.toDomainList(dtos)
            val groupedExpenses = expenses.groupBy { it.category }
            Result.Success(groupedExpenses)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
