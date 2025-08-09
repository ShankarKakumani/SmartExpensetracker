// AI-generated: Application-wide constants for the Smart Expense Tracker
package com.shankarkakumani.common.constants

/**
 * Application-wide constants used throughout the Smart Expense Tracker.
 */
object AppConstants {
    
    /**
     * Database-related constants.
     */
    object Database {
        const val DATABASE_NAME = "smart_expense_tracker_db"
        const val DATABASE_VERSION = 1
        const val EXPENSE_TABLE_NAME = "expenses"
    }
    
    /**
     * UI-related constants.
     */
    object UI {
        const val ANIMATION_DURATION_SHORT = 200
        const val ANIMATION_DURATION_MEDIUM = 300
        const val ANIMATION_DURATION_LONG = 500
        
        const val DEBOUNCE_DELAY_MS = 300L
        const val SEARCH_DELAY_MS = 500L
        
        const val MAX_TITLE_LENGTH = 100
        const val MAX_NOTES_LENGTH = 500
        const val MAX_AMOUNT_VALUE = 1_000_000.0
    }
    
    /**
     * Validation-related constants.
     */
    object Validation {
        const val MIN_TITLE_LENGTH = 2
        const val MAX_TITLE_LENGTH = 100
        const val MAX_NOTES_LENGTH = 500
        const val MAX_AMOUNT = 1_000_000.0
        const val DECIMAL_PLACES = 2
    }
    
    /**
     * Currency and formatting constants.
     */
    object Currency {
        const val CURRENCY_SYMBOL = "₹"
        const val CURRENCY_CODE = "INR"
        const val DECIMAL_FORMAT_PATTERN = "#,##,###.##"
    }
    
    /**
     * Date and time format constants.
     */
    object DateFormats {
        const val DISPLAY_DATE = "dd MMM yyyy"
        const val COMPACT_DATE = "dd/MM/yyyy"
        const val API_DATE = "yyyy-MM-dd"
        const val DATE_TIME = "dd MMM yyyy, HH:mm"
        const val TIME_ONLY = "HH:mm"
    }
    
    /**
     * Expense categories as constants.
     */
    object Categories {
        const val STAFF = "STAFF"
        const val TRAVEL = "TRAVEL"
        const val FOOD = "FOOD"
        const val UTILITY = "UTILITY"
        
        val ALL_CATEGORIES = listOf(STAFF, TRAVEL, FOOD, UTILITY)
        
        /**
         * Display names for categories.
         */
        val CATEGORY_DISPLAY_NAMES = mapOf(
            STAFF to "Staff",
            TRAVEL to "Travel",
            FOOD to "Food",
            UTILITY to "Utility"
        )
    }
    
    /**
     * Navigation-related constants.
     */
    object Navigation {
        const val EXPENSE_ENTRY_ROUTE = "expense_entry"
        const val EXPENSE_LIST_ROUTE = "expense_list"
        const val EXPENSE_REPORTS_ROUTE = "expense_reports"
        
        const val EXPENSE_ID_ARG = "expenseId"
    }
    
    /**
     * Error message constants.
     */
    object ErrorMessages {
        const val GENERIC_ERROR = "An unexpected error occurred"
        const val NETWORK_ERROR = "Please check your internet connection"
        const val VALIDATION_ERROR = "Please check your input and try again"
        const val SAVE_ERROR = "Failed to save expense. Please try again"
        const val LOAD_ERROR = "Failed to load data. Please try again"
        const val DELETE_ERROR = "Failed to delete expense. Please try again"
    }
    
    /**
     * Success message constants.
     */
    object SuccessMessages {
        const val EXPENSE_SAVED = "Expense saved successfully"
        const val EXPENSE_UPDATED = "Expense updated successfully"
        const val EXPENSE_DELETED = "Expense deleted successfully"
    }
    
    /**
     * Export-related constants.
     */
    object Export {
        const val CSV_MIME_TYPE = "text/csv"
        const val PDF_MIME_TYPE = "application/pdf"
        const val EXCEL_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        
        const val DEFAULT_EXPORT_FILENAME = "expense_report"
        const val CSV_EXTENSION = ".csv"
        const val PDF_EXTENSION = ".pdf"
        const val EXCEL_EXTENSION = ".xlsx"
    }
    
    /**
     * Chart-related constants.
     */
    object Charts {
        const val MAX_CHART_ENTRIES = 7 // For weekly charts
        const val CHART_ANIMATION_DURATION = 1000
        const val BAR_WIDTH = 0.8f
        const val MIN_CHART_VALUE = 0f
    }
    
    /**
     * Preferences keys for SharedPreferences.
     */
    object PreferenceKeys {
        const val THEME_MODE = "theme_mode"
        const val DEFAULT_CATEGORY = "default_category"
        const val FIRST_LAUNCH = "first_launch"
        const val LAST_BACKUP_DATE = "last_backup_date"
    }
    
    /**
     * Request codes for activities and permissions.
     */
    object RequestCodes {
        const val CAMERA_PERMISSION = 1001
        const val STORAGE_PERMISSION = 1002
        const val IMAGE_PICKER = 1003
        const val CAMERA_CAPTURE = 1004
    }
}
