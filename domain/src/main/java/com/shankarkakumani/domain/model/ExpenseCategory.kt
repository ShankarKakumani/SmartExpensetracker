// AI-generated: Expense category enum for the Smart Expense Tracker domain layer
package com.shankarkakumani.domain.model

/**
 * Represents the different categories of expenses that can be tracked.
 * This enum follows the requirements: Staff, Travel, Food, Utility.
 */
enum class ExpenseCategory(val displayName: String) {
    STAFF("Staff"),
    TRAVEL("Travel"),
    FOOD("Food"),
    UTILITY("Utility");
    
    companion object {
        /**
         * Returns all available categories as a list.
         */
        fun getAllCategories(): List<ExpenseCategory> = values().toList()
        
        /**
         * Converts a string to ExpenseCategory, case-insensitive.
         * @param value The string value to convert
         * @return ExpenseCategory or null if not found
         */
        fun fromString(value: String?): ExpenseCategory? {
            if (value.isNullOrBlank()) return null
            return values().find { 
                it.name.equals(value, ignoreCase = true) || 
                it.displayName.equals(value, ignoreCase = true)
            }
        }
        
        /**
         * Returns the default category (can be used for initial selection).
         */
        fun getDefault(): ExpenseCategory = FOOD
    }
}
