// AI-generated: Room Entity for Expense
package com.shankarkakumani.data.client.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val amount: Double,
    val category: String,
    val notes: String?,
    val receiptImageUrl: String?,
    val timestamp: Long
)


