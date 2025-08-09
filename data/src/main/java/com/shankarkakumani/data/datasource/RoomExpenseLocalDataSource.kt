// AI-generated: Room-backed implementation of ExpenseLocalDataSource
package com.shankarkakumani.data.datasource

import com.shankarkakumani.data.client.room.dao.ExpenseDao
import com.shankarkakumani.data.client.room.entity.ExpenseEntity
import com.shankarkakumani.data.model.ExpenseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomExpenseLocalDataSource @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseLocalDataSource {

    override val expensesFlow: Flow<List<ExpenseDto>> = expenseDao.observeAll().map { it.map(ExpenseEntity::toDto) }

    override suspend fun addExpense(expense: ExpenseDto): ExpenseDto {
        expenseDao.upsert(expense.toEntity())
        return expense
    }

    override suspend fun getAllExpenses(): List<ExpenseDto> = expenseDao.getAll().map(ExpenseEntity::toDto)

    override suspend fun getExpensesByDateRange(startDate: Long, endDate: Long): List<ExpenseDto> =
        expenseDao.getByDateRange(startDate, endDate).map(ExpenseEntity::toDto)

    override suspend fun getExpensesByCategory(category: String): List<ExpenseDto> =
        expenseDao.getByCategory(category).map(ExpenseEntity::toDto)

    override suspend fun getExpensesByDateRangeAndCategory(startDate: Long, endDate: Long, category: String): List<ExpenseDto> =
        expenseDao.getByDateRangeAndCategory(startDate, endDate, category).map(ExpenseEntity::toDto)

    override suspend fun updateExpense(expense: ExpenseDto): ExpenseDto? {
        val updated = expenseDao.update(expense.toEntity())
        return if (updated > 0) expense else null
    }

    override suspend fun deleteExpense(expenseId: String): Boolean = expenseDao.deleteById(expenseId) > 0

    override suspend fun searchExpenses(query: String): List<ExpenseDto> = expenseDao.search(query).map(ExpenseEntity::toDto)

    override suspend fun getExpenseCountByDateRange(startDate: Long, endDate: Long): Int = expenseDao.countByDateRange(startDate, endDate)

    override suspend fun clearAllExpenses() { expenseDao.clear() }
}

private fun ExpenseEntity.toDto(): ExpenseDto = ExpenseDto(
    id = id,
    title = title,
    amount = amount,
    category = category,
    notes = notes,
    receiptImageUrl = receiptImageUrl,
    timestamp = timestamp
)

private fun ExpenseDto.toEntity(): ExpenseEntity = ExpenseEntity(
    id = id,
    title = title,
    amount = amount,
    category = category,
    notes = notes,
    receiptImageUrl = receiptImageUrl,
    timestamp = timestamp
)


