// AI-generated: UI state for expense entry screen
package com.shankarkakumani.smartexpensetracker.presentation.expense_entry

import com.shankarkakumani.domain.model.ExpenseCategory

/**
 * UI state for the expense entry screen.
 * Contains form data, validation states, and UI feedback.
 */
data class ExpenseEntryUiState(
    // Form fields
    val title: String = "",
    val amount: String = "",
    val selectedCategory: ExpenseCategory = ExpenseCategory.getDefault(),
    val notes: String = "",
    val receiptImageUrl: String? = null,
    
    // Validation states
    val titleError: String? = null,
    val amountError: String? = null,
    val notesError: String? = null,
    
    // UI states
    val isLoading: Boolean = false,
    val isSubmitEnabled: Boolean = false,
    val showCategoryDropdown: Boolean = false,
    
    // Real-time data
    val totalSpentToday: Double = 0.0,
    val todayExpenseCount: Int = 0,
    
    // Feedback
    val successMessage: String? = null,
    val errorMessage: String? = null
) {
    
    /**
     * Returns true if all required fields are valid.
     */
    val isFormValid: Boolean
        get() = title.isNotBlank() && 
                amount.isNotBlank() && 
                amount.toDoubleOrNull()?.let { it > 0 } == true &&
                titleError == null && 
                amountError == null && 
                notesError == null
    
    /**
     * Returns formatted total spent today with currency.
     */
    fun getFormattedTotalToday(): String = "₹${String.format("%.2f", totalSpentToday)}"
    
    /**
     * Returns a summary of today's expenses.
     */
    fun getTodaySummary(): String {
        return if (todayExpenseCount == 0) {
            "No expenses today"
        } else {
            "${getFormattedTotalToday()} spent across $todayExpenseCount expense${if (todayExpenseCount > 1) "s" else ""} today"
        }
    }
    
    /**
     * Returns true if any validation errors exist.
     */
    val hasValidationErrors: Boolean
        get() = titleError != null || amountError != null || notesError != null
}

/**
 * Sealed class for expense entry actions.
 * Represents all possible user interactions.
 */
sealed class ExpenseEntryAction {
    data class UpdateTitle(val title: String) : ExpenseEntryAction()
    data class UpdateAmount(val amount: String) : ExpenseEntryAction()
    data class UpdateCategory(val category: ExpenseCategory) : ExpenseEntryAction()
    data class UpdateNotes(val notes: String) : ExpenseEntryAction()
    data class UpdateReceiptUrl(val url: String?) : ExpenseEntryAction()
    
    object ToggleCategoryDropdown : ExpenseEntryAction()
    object SubmitExpense : ExpenseEntryAction()
    object ClearMessages : ExpenseEntryAction()
    object RefreshTotalToday : ExpenseEntryAction()
    
    // Form actions
    object ClearForm : ExpenseEntryAction()
    object ValidateForm : ExpenseEntryAction()
}
