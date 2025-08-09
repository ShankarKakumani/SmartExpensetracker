// AI-generated: Room Database for Smart Expense Tracker
package com.shankarkakumani.data.client.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shankarkakumani.data.client.room.entity.ExpenseEntity
import com.shankarkakumani.data.client.room.dao.ExpenseDao
import com.shankarkakumani.data.client.room.RoomConfig

@Database(
    entities = [ExpenseEntity::class],
    version = RoomConfig.DATABASE_VERSION,
    exportSchema = false
)
abstract class SmartExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}


