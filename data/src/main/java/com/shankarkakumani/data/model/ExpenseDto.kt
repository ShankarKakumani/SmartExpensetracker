// AI-generated: Data Transfer Object for Expense entity
package com.shankarkakumani.data.model

/**
 * Data Transfer Object for Expense.
 * This mirrors the domain model but is used for data persistence and transfer.
 * Kept simple without business logic to maintain separation of concerns.
 */
data class ExpenseDto(
    val id: String,
    val title: String,
    val amount: Double,
    val category: String, // String representation of ExpenseCategory
    val notes: String?,
    val receiptImageUrl: String?,
    val timestamp: Long
) {
    companion object {
        /**
         * Creates an empty ExpenseDto for testing or default scenarios.
         */
        fun empty(): ExpenseDto = ExpenseDto(
            id = "",
            title = "",
            amount = 0.0,
            category = "FOOD",
            notes = null,
            receiptImageUrl = null,
            timestamp = 0L
        )
    }
}
