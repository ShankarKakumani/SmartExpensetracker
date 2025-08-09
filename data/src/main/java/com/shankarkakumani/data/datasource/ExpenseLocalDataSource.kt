// AI-generated: Local data source abstraction to back repository with Room or in-memory implementations
package com.shankarkakumani.data.datasource

import com.shankarkakumani.data.model.ExpenseDto
import kotlinx.coroutines.flow.Flow

interface ExpenseLocalDataSource {
    val expensesFlow: Flow<List<ExpenseDto>>

    suspend fun addExpense(expense: ExpenseDto): ExpenseDto
    suspend fun getAllExpenses(): List<ExpenseDto>
    suspend fun getExpensesByDateRange(startDate: Long, endDate: Long): List<ExpenseDto>
    suspend fun getExpensesByCategory(category: String): List<ExpenseDto>
    suspend fun getExpensesByDateRangeAndCategory(startDate: Long, endDate: Long, category: String): List<ExpenseDto>
    suspend fun updateExpense(expense: ExpenseDto): ExpenseDto?
    suspend fun deleteExpense(expenseId: String): Boolean
    suspend fun searchExpenses(query: String): List<ExpenseDto>
    suspend fun getExpenseCountByDateRange(startDate: Long, endDate: Long): Int
    suspend fun clearAllExpenses()
}


