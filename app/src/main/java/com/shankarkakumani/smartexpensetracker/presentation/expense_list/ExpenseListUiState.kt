// AI-generated: Comprehensive UI state for expense list with filtering and grouping
package com.shankarkakumani.smartexpensetracker.presentation.expense_list

import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import java.time.LocalDate

/**
 * UI state for the expense list screen with comprehensive filtering and grouping options.
 */
data class ExpenseListUiState(
    val expenses: List<Expense> = emptyList(),
    val filteredExpenses: List<Expense> = emptyList(),
    val groupedExpenses: Map<String, List<Expense>> = emptyMap(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    
    // Filter options
    val selectedDate: LocalDate? = null,
    val selectedCategory: ExpenseCategory? = null,
    val searchQuery: String = "",
    val showDatePicker: Boolean = false,
    val showCategoryFilter: Boolean = false,
    
    // Grouping options
    val groupingMode: GroupingMode = GroupingMode.NONE,
    val showGroupingOptions: Boolean = false,
    
    // View options
    val viewMode: ViewMode = ViewMode.LIST,
    val sortOrder: SortOrder = SortOrder.NEWEST_FIRST,
    
    // Summary data
    val totalAmount: Double = 0.0,
    val totalExpenses: Int = 0,
    val todayExpenseCount: Int = 0,
    val todayTotal: Double = 0.0,
) {
    
    /**
     * Returns formatted total amount with currency.
     */
    fun getFormattedTotal(): String = "₹${String.format("%.2f", totalAmount)}"
    
    /**
     * Returns formatted today's total with currency.
     */
    fun getFormattedTodayTotal(): String = "₹${String.format("%.2f", todayTotal)}"
    
    /**
     * Returns summary text for the expense list.
     */
    fun getSummaryText(): String {
        return when {
            totalExpenses == 0 -> "No expenses found"
            selectedDate != null -> "$totalExpenses expenses on ${selectedDate}"
            selectedCategory != null -> "$totalExpenses ${selectedCategory.displayName} expenses"
            searchQuery.isNotEmpty() -> "$totalExpenses expenses matching '$searchQuery'"
            else -> "$totalExpenses total expenses"
        }
    }
    
    /**
     * Returns whether any filters are active.
     */
    fun hasActiveFilters(): Boolean {
        return selectedDate != null || selectedCategory != null || searchQuery.isNotEmpty()
    }
    
    /**
     * Returns the current screen title based on active filters.
     */
    fun getScreenTitle(): String {
        return when {
            selectedDate != null -> "Expenses - ${selectedDate}"
            selectedCategory != null -> "${selectedCategory.displayName} Expenses"
            searchQuery.isNotEmpty() -> "Search Results"
            else -> "All Expenses"
        }
    }
    
    /**
     * Returns whether empty state should be shown.
     */
    fun shouldShowEmptyState(): Boolean {
        return !isLoading && filteredExpenses.isEmpty() && errorMessage == null
    }
    
    /**
     * Returns whether content should be shown.
     */
    fun shouldShowContent(): Boolean {
        return !isLoading && filteredExpenses.isNotEmpty() && errorMessage == null
    }
}

/**
 * Represents different grouping modes for expenses.
 */
enum class GroupingMode(val displayName: String) {
    NONE("No Grouping"),
    BY_CATEGORY("By Category"),
    BY_DATE("By Date"),
    BY_AMOUNT("By Amount Range");
    
    companion object {
        fun getAllModes() = values().toList()
    }
}

/**
 * Represents different view modes for displaying expenses.
 */
enum class ViewMode(val displayName: String) {
    LIST("List View"),
    GRID("Grid View"),
    COMPACT("Compact View");
    
    companion object {
        fun getAllModes() = values().toList()
    }
}

/**
 * Represents different sorting orders for expenses.
 */
enum class SortOrder(val displayName: String) {
    NEWEST_FIRST("Newest First"),
    OLDEST_FIRST("Oldest First"),
    AMOUNT_HIGH_TO_LOW("Amount: High to Low"),
    AMOUNT_LOW_TO_HIGH("Amount: Low to High"),
    ALPHABETICAL("Alphabetical");
    
    companion object {
        fun getAllOrders() = values().toList()
    }
}

/**
 * Represents different actions that can be performed on the expense list.
 */
sealed class ExpenseListAction {
    // Data actions
    object LoadExpenses : ExpenseListAction()
    object RefreshExpenses : ExpenseListAction()
    object ClearMessages : ExpenseListAction()
    
    // Filter actions
    data class UpdateSearchQuery(val query: String) : ExpenseListAction()
    data class SelectDate(val date: LocalDate?) : ExpenseListAction()
    data class SelectCategory(val category: ExpenseCategory?) : ExpenseListAction()
    object ToggleDatePicker : ExpenseListAction()
    object ToggleCategoryFilter : ExpenseListAction()
    object ClearAllFilters : ExpenseListAction()
    
    // Grouping actions
    data class ChangeGroupingMode(val mode: GroupingMode) : ExpenseListAction()
    object ToggleGroupingOptions : ExpenseListAction()
    
    // View actions
    data class ChangeViewMode(val mode: ViewMode) : ExpenseListAction()
    data class ChangeSortOrder(val order: SortOrder) : ExpenseListAction()
    
    // Navigation actions
    data class NavigateToExpenseDetail(val expenseId: String) : ExpenseListAction()
    object NavigateToAddExpense : ExpenseListAction()
    
    // Item actions
    data class DeleteExpense(val expenseId: String) : ExpenseListAction()
    data class EditExpense(val expense: Expense) : ExpenseListAction()
}
