// AI-generated: ViewModel for expense entry screen
package com.shankarkakumani.smartexpensetracker.presentation.expense_entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.domain.usecase.AddExpenseUseCase
import com.shankarkakumani.domain.usecase.GetTotalSpentTodayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the expense entry screen.
 * Manages form state, validation, and expense submission.
 */
@HiltViewModel
class ExpenseEntryViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val getTotalSpentTodayUseCase: GetTotalSpentTodayUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ExpenseEntryUiState())
    val uiState: StateFlow<ExpenseEntryUiState> = _uiState.asStateFlow()
    
    init {
        // Load today's total on initialization
        refreshTotalToday()
    }
    
    /**
     * Handles all user actions from the UI.
     */
    fun onAction(action: ExpenseEntryAction) {
        when (action) {
            is ExpenseEntryAction.UpdateTitle -> updateTitle(action.title)
            is ExpenseEntryAction.UpdateAmount -> updateAmount(action.amount)
            is ExpenseEntryAction.UpdateCategory -> updateCategory(action.category)
            is ExpenseEntryAction.UpdateNotes -> updateNotes(action.notes)
            is ExpenseEntryAction.UpdateReceiptUrl -> updateReceiptUrl(action.url)
            is ExpenseEntryAction.ToggleCategoryDropdown -> toggleCategoryDropdown()
            is ExpenseEntryAction.SubmitExpense -> submitExpense()
            is ExpenseEntryAction.ClearMessages -> clearMessages()
            is ExpenseEntryAction.RefreshTotalToday -> refreshTotalToday()
            is ExpenseEntryAction.ClearForm -> clearForm()
            is ExpenseEntryAction.ValidateForm -> validateForm()
        }
    }
    
    private fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(
            title = title,
            titleError = validateTitle(title)
        )
        updateSubmitEnabled()
    }
    
    private fun updateAmount(amount: String) {
        // Allow only numeric input with decimal point
        val filteredAmount = amount.filter { it.isDigit() || it == '.' }
        
        _uiState.value = _uiState.value.copy(
            amount = filteredAmount,
            amountError = validateAmount(filteredAmount)
        )
        updateSubmitEnabled()
    }
    
    private fun updateCategory(category: ExpenseCategory) {
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            showCategoryDropdown = false
        )
    }
    
    private fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(
            notes = notes,
            notesError = validateNotes(notes)
        )
        updateSubmitEnabled()
    }
    
    private fun updateReceiptUrl(url: String?) {
        _uiState.value = _uiState.value.copy(receiptImageUrl = url)
    }
    
    private fun toggleCategoryDropdown() {
        _uiState.value = _uiState.value.copy(
            showCategoryDropdown = !_uiState.value.showCategoryDropdown
        )
    }
    
    private fun submitExpense() {
        val currentState = _uiState.value
        
        if (!currentState.isFormValid) {
            validateForm()
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )
            
            try {
                val result = addExpenseUseCase(
                    title = currentState.title.trim(),
                    amount = currentState.amount.toDouble(),
                    category = currentState.selectedCategory,
                    notes = currentState.notes.trim().takeIf { it.isNotBlank() },
                    receiptImageUrl = currentState.receiptImageUrl
                )
                
                when (result) {
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            successMessage = "Expense added successfully!"
                        )
                        clearForm()
                        refreshTotalToday()
                    }
                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.exception.message ?: "Failed to add expense"
                        )
                    }
                    is Result.Loading -> {
                        // Already handled above
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "An unexpected error occurred"
                )
            }
        }
    }
    
    private fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }
    
    private fun refreshTotalToday() {
        viewModelScope.launch {
            try {
                val summaryResult = getTotalSpentTodayUseCase.getTodaySpendingSummary()
                
                when (summaryResult) {
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            totalSpentToday = summaryResult.data.totalAmount,
                            todayExpenseCount = summaryResult.data.expenseCount
                        )
                    }
                    is Result.Error -> {
                        // Silently handle error, keep previous values
                    }
                    is Result.Loading -> {
                        // Handle if needed
                    }
                }
            } catch (e: Exception) {
                // Silently handle error
            }
        }
    }
    
    private fun clearForm() {
        _uiState.value = _uiState.value.copy(
            title = "",
            amount = "",
            selectedCategory = ExpenseCategory.getDefault(),
            notes = "",
            receiptImageUrl = null,
            titleError = null,
            amountError = null,
            notesError = null,
            showCategoryDropdown = false
        )
        updateSubmitEnabled()
    }
    
    private fun validateForm() {
        val currentState = _uiState.value
        
        _uiState.value = _uiState.value.copy(
            titleError = validateTitle(currentState.title),
            amountError = validateAmount(currentState.amount),
            notesError = validateNotes(currentState.notes)
        )
        updateSubmitEnabled()
    }
    
    private fun updateSubmitEnabled() {
        _uiState.value = _uiState.value.copy(
            isSubmitEnabled = _uiState.value.isFormValid && !_uiState.value.isLoading
        )
    }
    
    // Validation helper functions
    private fun validateTitle(title: String): String? {
        return when {
            title.isBlank() -> "Title is required"
            title.length < 2 -> "Title must be at least 2 characters"
            title.length > 50 -> "Title must be less than 50 characters"
            else -> null
        }
    }
    
    private fun validateAmount(amount: String): String? {
        return when {
            amount.isBlank() -> "Amount is required"
            amount.toDoubleOrNull() == null -> "Please enter a valid amount"
            amount.toDouble() <= 0 -> "Amount must be greater than 0"
            amount.toDouble() > 100000 -> "Amount seems too large"
            amount.count { it == '.' } > 1 -> "Invalid decimal format"
            else -> null
        }
    }
    
    private fun validateNotes(notes: String): String? {
        return when {
            notes.length > 100 -> "Notes cannot exceed 100 characters"
            else -> null
        }
    }
}
