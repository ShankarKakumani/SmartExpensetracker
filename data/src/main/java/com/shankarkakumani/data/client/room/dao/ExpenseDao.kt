// AI-generated: Room DAO for Expense operations
package com.shankarkakumani.data.client.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.shankarkakumani.data.client.room.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses ORDER BY timestamp DESC")
    suspend fun getAll(): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE timestamp BETWEEN :start AND :end ORDER BY timestamp DESC")
    suspend fun getByDateRange(start: Long, end: Long): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY timestamp DESC")
    suspend fun getByCategory(category: String): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE (timestamp BETWEEN :start AND :end) AND category = :category ORDER BY timestamp DESC")
    suspend fun getByDateRangeAndCategory(start: Long, end: Long, category: String): List<ExpenseEntity>

    @Query("SELECT COUNT(*) FROM expenses WHERE timestamp BETWEEN :start AND :end")
    suspend fun countByDateRange(start: Long, end: Long): Int

    @Query("SELECT * FROM expenses WHERE title LIKE '%' || :query || '%' OR notes LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    suspend fun search(query: String): List<ExpenseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(expense: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(expenses: List<ExpenseEntity>)

    @Update
    suspend fun update(expense: ExpenseEntity): Int

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun deleteById(id: String): Int

    @Query("DELETE FROM expenses")
    suspend fun clear()
}


