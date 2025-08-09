// AI-generated: Validation utilities for expense tracking form validation
package com.shankarkakumani.common.validation

/**
 * Validation utilities for expense tracking forms and data.
 */
object ValidationUtils {
    
    /**
     * Validates an expense title.
     * @param title The title to validate
     * @return ValidationResult with error message if invalid
     */
    fun validateExpenseTitle(title: String): ValidationResult {
        return when {
            title.isBlank() -> ValidationResult.Error("Title cannot be empty")
            title.length < 2 -> ValidationResult.Error("Title must be at least 2 characters")
            title.length > 100 -> ValidationResult.Error("Title must be less than 100 characters")
            else -> ValidationResult.Valid
        }
    }
    
    /**
     * Validates an expense amount.
     * @param amount The amount to validate
     * @return ValidationResult with error message if invalid
     */
    fun validateExpenseAmount(amount: String): ValidationResult {
        if (amount.isBlank()) {
            return ValidationResult.Error("Amount cannot be empty")
        }
        
        val amountValue = amount.toDoubleOrNull()
        return when {
            amountValue == null -> ValidationResult.Error("Please enter a valid amount")
            amountValue <= 0 -> ValidationResult.Error("Amount must be greater than zero")
            amountValue > 1_000_000 -> ValidationResult.Error("Amount cannot exceed ₹10,00,000")
            else -> ValidationResult.Valid
        }
    }
    
    /**
     * Validates expense notes.
     * @param notes The notes to validate
     * @return ValidationResult with error message if invalid
     */
    fun validateExpenseNotes(notes: String): ValidationResult {
        return when {
            notes.length > 500 -> ValidationResult.Error("Notes must be less than 500 characters")
            else -> ValidationResult.Valid
        }
    }
    
    /**
     * Validates if a category is selected.
     * @param category The selected category
     * @return ValidationResult with error message if invalid
     */
    fun validateCategory(category: String?): ValidationResult {
        return when {
            category.isNullOrBlank() -> ValidationResult.Error("Please select a category")
            else -> ValidationResult.Valid
        }
    }
    
    /**
     * Validates all expense form fields at once.
     * @param title The expense title
     * @param amount The expense amount
     * @param category The selected category
     * @param notes The expense notes
     * @return List of validation errors, empty if all valid
     */
    fun validateExpenseForm(
        title: String,
        amount: String,
        category: String?,
        notes: String
    ): List<String> {
        val errors = mutableListOf<String>()
        
        validateExpenseTitle(title).let { result ->
            if (result is ValidationResult.Error) {
                errors.add(result.message)
            }
        }
        
        validateExpenseAmount(amount).let { result ->
            if (result is ValidationResult.Error) {
                errors.add(result.message)
            }
        }
        
        validateCategory(category).let { result ->
            if (result is ValidationResult.Error) {
                errors.add(result.message)
            }
        }
        
        validateExpenseNotes(notes).let { result ->
            if (result is ValidationResult.Error) {
                errors.add(result.message)
            }
        }
        
        return errors
    }
    
    /**
     * Checks if an amount string represents a valid positive number.
     */
    fun isValidAmount(amount: String): Boolean {
        val amountValue = amount.toDoubleOrNull()
        return amountValue != null && amountValue > 0
    }
    
    /**
     * Formats an amount string to ensure proper decimal formatting.
     * @param amount The raw amount string
     * @return Formatted amount string or the original if invalid
     */
    fun formatAmountInput(amount: String): String {
        if (amount.isEmpty()) return amount
        
        // Remove any non-digit and non-decimal characters except the first decimal point
        val cleaned = amount.filter { it.isDigit() || it == '.' }
        
        // Ensure only one decimal point
        val decimalIndex = cleaned.indexOf('.')
        if (decimalIndex != -1) {
            val beforeDecimal = cleaned.substring(0, decimalIndex)
            val afterDecimal = cleaned.substring(decimalIndex + 1).filter { it.isDigit() }
            
            // Limit to 2 decimal places
            val limitedAfterDecimal = if (afterDecimal.length > 2) {
                afterDecimal.substring(0, 2)
            } else {
                afterDecimal
            }
            
            return if (limitedAfterDecimal.isNotEmpty()) {
                "$beforeDecimal.$limitedAfterDecimal"
            } else {
                beforeDecimal
            }
        }
        
        return cleaned
    }
}

/**
 * Represents the result of a validation operation.
 */
sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

/**
 * Extension function to check if validation result is valid.
 */
fun ValidationResult.isValid(): Boolean = this is ValidationResult.Valid

/**
 * Extension function to get error message from validation result.
 */
fun ValidationResult.getErrorMessage(): String? = when (this) {
    is ValidationResult.Error -> message
    is ValidationResult.Valid -> null
}
