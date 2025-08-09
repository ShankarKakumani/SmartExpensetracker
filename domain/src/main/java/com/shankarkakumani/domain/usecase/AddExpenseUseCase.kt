// AI-generated: Use case for adding new expenses with validation
package com.shankarkakumani.domain.usecase

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.domain.repository.ExpenseRepository
import javax.inject.Inject

/**
 * Use case for adding a new expense with proper validation.
 * Encapsulates business logic for expense creation and validation.
 */
class AddExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    
    /**
     * Adds a new expense after validation.
     * @param title The expense title (required, non-empty)
     * @param amount The expense amount (required, > 0)
     * @param category The expense category (required)
     * @param notes Optional notes (max 100 characters)
     * @param receiptImageUrl Optional receipt image URL
     * @return Result containing the created expense or validation error
     */
    suspend operator fun invoke(
        title: String,
        amount: Double,
        category: ExpenseCategory,
        notes: String? = null,
        receiptImageUrl: String? = null
    ): Result<Expense> {
        
        // Validation
        val validationError = validateInput(title, amount, notes)
        if (validationError != null) {
            return Result.Error(IllegalArgumentException(validationError))
        }
        
        return try {
            val expense = Expense.create(
                title = title,
                amount = amount,
                category = category,
                notes = notes,
                receiptImageUrl = receiptImageUrl
            )
            
            expenseRepository.addExpense(expense)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    /**
     * Validates the input parameters according to business rules.
     * @return Error message if validation fails, null if valid
     */
    private fun validateInput(title: String, amount: Double, notes: String?): String? {
        return when {
            title.isBlank() -> "Title cannot be empty"
            amount <= 0 -> "Amount must be greater than 0"
            notes != null && notes.length > 100 -> "Notes cannot exceed 100 characters"
            else -> null
        }
    }
    
    /**
     * Data class for expense creation parameters.
     * Useful for form handling in the presentation layer.
     */
    data class ExpenseParams(
        val title: String,
        val amount: Double,
        val category: ExpenseCategory,
        val notes: String? = null,
        val receiptImageUrl: String? = null
    )
    
    /**
     * Alternative invoke method using ExpenseParams.
     * @param params The expense parameters
     * @return Result containing the created expense or validation error
     */
    suspend operator fun invoke(params: ExpenseParams): Result<Expense> {
        return invoke(
            title = params.title,
            amount = params.amount,
            category = params.category,
            notes = params.notes,
            receiptImageUrl = params.receiptImageUrl
        )
    }
}
