// AI-generated: Core Expense model for the Smart Expense Tracker domain layer
package com.shankarkakumani.domain.model

import java.util.UUID

/**
 * Represents an expense entry in the Smart Expense Tracker.
 * This is the core domain model that encapsulates all expense-related data.
 */
data class Expense(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val amount: Double,
    val category: ExpenseCategory,
    val notes: String? = null,
    val receiptImageUrl: String? = null,
    val timestamp: Long = System.currentTimeMillis()
) {
    
    /**
     * Validates the expense data according to business rules.
     * @return True if the expense is valid, false otherwise
     */
    fun isValid(): Boolean {
        return title.isNotBlank() && 
               amount > 0 && 
               (notes?.length ?: 0) <= 100
    }
    
    /**
     * Returns a formatted amount string with currency symbol.
     */
    fun getFormattedAmount(): String = "₹${String.format("%.2f", amount)}"
    
    companion object {
        /**
         * Creates an expense with validation.
         * Throws IllegalArgumentException if validation fails.
         */
        fun create(
            title: String,
            amount: Double,
            category: ExpenseCategory,
            notes: String? = null,
            receiptImageUrl: String? = null
        ): Expense {
            require(title.isNotBlank()) { "Title cannot be empty" }
            require(amount > 0) { "Amount must be greater than 0" }
            require(notes?.length ?: 0 <= 100) { "Notes cannot exceed 100 characters" }
            
            return Expense(
                title = title.trim(),
                amount = amount,
                category = category,
                notes = notes?.trim()?.takeIf { it.isNotBlank() },
                receiptImageUrl = receiptImageUrl?.trim()?.takeIf { it.isNotBlank() }
            )
        }
    }
}
