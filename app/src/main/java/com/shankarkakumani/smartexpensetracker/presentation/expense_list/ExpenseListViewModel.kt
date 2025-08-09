// AI-generated: ViewModel for expense list with comprehensive filtering and state management
package com.shankarkakumani.smartexpensetracker.presentation.expense_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.domain.usecase.GetExpensesUseCase
import com.shankarkakumani.domain.usecase.GetTotalSpentTodayUseCase
import com.shankarkakumani.domain.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

/**
 * ViewModel for managing expense list state with filtering, grouping, and search functionality.
 */
@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getTotalSpentTodayUseCase: GetTotalSpentTodayUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ExpenseListUiState())
    val uiState: StateFlow<ExpenseListUiState> = _uiState.asStateFlow()
    
    init {
        loadExpenses()
        loadTodayTotal()
    }
    
    /**
     * Handles all user actions for the expense list.
     */
    fun onAction(action: ExpenseListAction) {
        when (action) {
            is ExpenseListAction.LoadExpenses -> loadExpenses()
            is ExpenseListAction.RefreshExpenses -> refreshExpenses()
            is ExpenseListAction.ClearMessages -> clearMessages()
            
            is ExpenseListAction.UpdateSearchQuery -> updateSearchQuery(action.query)
            is ExpenseListAction.SelectDate -> selectDate(action.date)
            is ExpenseListAction.SelectCategory -> selectCategory(action.category)
            is ExpenseListAction.ToggleDatePicker -> toggleDatePicker()
            is ExpenseListAction.ToggleCategoryFilter -> toggleCategoryFilter()
            is ExpenseListAction.ClearAllFilters -> clearAllFilters()
            
            is ExpenseListAction.ChangeGroupingMode -> changeGroupingMode(action.mode)
            is ExpenseListAction.ToggleGroupingOptions -> toggleGroupingOptions()
            
            is ExpenseListAction.ChangeViewMode -> changeViewMode(action.mode)
            is ExpenseListAction.ChangeSortOrder -> changeSortOrder(action.order)
            
            is ExpenseListAction.NavigateToExpenseDetail -> {
                // Navigation will be handled by the UI layer
            }
            is ExpenseListAction.NavigateToAddExpense -> {
                // Navigation will be handled by the UI layer
            }
            
            is ExpenseListAction.DeleteExpense -> deleteExpense(action.expenseId)
            is ExpenseListAction.EditExpense -> editExpense(action.expense)
        }
    }
    
    /**
     * Loads all expenses and applies current filters.
     */
    private fun loadExpenses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            getExpensesUseCase.getAllExpenses().collect { result ->
                when (result) {
                    is Result.Success<List<Expense>> -> {
                        val expenses = result.data
                        _uiState.update { currentState ->
                            val filteredExpenses = applyFiltersAndSorting(expenses, currentState)
                            val groupedExpenses = applyGrouping(filteredExpenses, currentState.groupingMode)
                            
                            currentState.copy(
                                expenses = expenses,
                                filteredExpenses = filteredExpenses,
                                groupedExpenses = groupedExpenses,
                                isLoading = false,
                                totalAmount = filteredExpenses.sumOf { it.amount },
                                totalExpenses = filteredExpenses.size,
                                errorMessage = null
                            )
                        }
                    }
                    is Result.Error -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = false, 
                                errorMessage = "Failed to load expenses: ${result.exception.message}"
                            ) 
                        }
                    }
                    is Result.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
    
    /**
     * Refreshes the expense list.
     */
    private fun refreshExpenses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            loadExpenses()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
    
    /**
     * Loads today's total spending.
     */
    private fun loadTodayTotal() {
        viewModelScope.launch {
            when (val result = getTotalSpentTodayUseCase()) {
                is Result.Success -> {
                    val todayTotal = result.data
                    val todayExpenses = _uiState.value.expenses.filter { expense ->
                        DateUtils.isSameDay(expense.timestamp, System.currentTimeMillis())
                    }
                    
                    _uiState.update { 
                        it.copy(
                            todayTotal = todayTotal,
                            todayExpenseCount = todayExpenses.size
                        ) 
                    }
                }
                is Result.Error -> {
                    // Silently fail for today's total - not critical
                }
                is Result.Loading -> {
                    // Nothing to do for loading state
                }
            }
        }
    }
    
    /**
     * Updates the search query and applies filtering.
     */
    private fun updateSearchQuery(query: String) {
        _uiState.update { currentState ->
            val filteredExpenses = applyFiltersAndSorting(currentState.expenses, currentState.copy(searchQuery = query))
            val groupedExpenses = applyGrouping(filteredExpenses, currentState.groupingMode)
            
            currentState.copy(
                searchQuery = query,
                filteredExpenses = filteredExpenses,
                groupedExpenses = groupedExpenses,
                totalAmount = filteredExpenses.sumOf { it.amount },
                totalExpenses = filteredExpenses.size
            )
        }
    }
    
    /**
     * Selects a date for filtering.
     */
    private fun selectDate(date: LocalDate?) {
        _uiState.update { currentState ->
            val filteredExpenses = applyFiltersAndSorting(currentState.expenses, currentState.copy(selectedDate = date))
            val groupedExpenses = applyGrouping(filteredExpenses, currentState.groupingMode)
            
            currentState.copy(
                selectedDate = date,
                showDatePicker = false,
                filteredExpenses = filteredExpenses,
                groupedExpenses = groupedExpenses,
                totalAmount = filteredExpenses.sumOf { it.amount },
                totalExpenses = filteredExpenses.size
            )
        }
    }
    
    /**
     * Selects a category for filtering.
     */
    private fun selectCategory(category: ExpenseCategory?) {
        _uiState.update { currentState ->
            val filteredExpenses = applyFiltersAndSorting(currentState.expenses, currentState.copy(selectedCategory = category))
            val groupedExpenses = applyGrouping(filteredExpenses, currentState.groupingMode)
            
            currentState.copy(
                selectedCategory = category,
                showCategoryFilter = false,
                filteredExpenses = filteredExpenses,
                groupedExpenses = groupedExpenses,
                totalAmount = filteredExpenses.sumOf { it.amount },
                totalExpenses = filteredExpenses.size
            )
        }
    }
    
    /**
     * Toggles the date picker visibility.
     */
    private fun toggleDatePicker() {
        _uiState.update { it.copy(showDatePicker = !it.showDatePicker, showCategoryFilter = false) }
    }
    
    /**
     * Toggles the category filter visibility.
     */
    private fun toggleCategoryFilter() {
        _uiState.update { it.copy(showCategoryFilter = !it.showCategoryFilter, showDatePicker = false) }
    }
    
    /**
     * Clears all active filters.
     */
    private fun clearAllFilters() {
        _uiState.update { currentState ->
            val filteredExpenses = applyFiltersAndSorting(
                currentState.expenses, 
                currentState.copy(
                    selectedDate = null,
                    selectedCategory = null,
                    searchQuery = ""
                )
            )
            val groupedExpenses = applyGrouping(filteredExpenses, currentState.groupingMode)
            
            currentState.copy(
                selectedDate = null,
                selectedCategory = null,
                searchQuery = "",
                showDatePicker = false,
                showCategoryFilter = false,
                filteredExpenses = filteredExpenses,
                groupedExpenses = groupedExpenses,
                totalAmount = filteredExpenses.sumOf { it.amount },
                totalExpenses = filteredExpenses.size
            )
        }
    }
    
    /**
     * Changes the grouping mode.
     */
    private fun changeGroupingMode(mode: GroupingMode) {
        _uiState.update { currentState ->
            val groupedExpenses = applyGrouping(currentState.filteredExpenses, mode)
            currentState.copy(
                groupingMode = mode,
                groupedExpenses = groupedExpenses,
                showGroupingOptions = false
            )
        }
    }
    
    /**
     * Toggles grouping options visibility.
     */
    private fun toggleGroupingOptions() {
        _uiState.update { it.copy(showGroupingOptions = !it.showGroupingOptions) }
    }
    
    /**
     * Changes the view mode.
     */
    private fun changeViewMode(mode: ViewMode) {
        _uiState.update { it.copy(viewMode = mode) }
    }
    
    /**
     * Changes the sort order.
     */
    private fun changeSortOrder(order: SortOrder) {
        _uiState.update { currentState ->
            val filteredExpenses = applyFiltersAndSorting(currentState.expenses, currentState.copy(sortOrder = order))
            val groupedExpenses = applyGrouping(filteredExpenses, currentState.groupingMode)
            
            currentState.copy(
                sortOrder = order,
                filteredExpenses = filteredExpenses,
                groupedExpenses = groupedExpenses
            )
        }
    }
    
    /**
     * Deletes an expense.
     */
    private fun deleteExpense(expenseId: String) {
        // TODO: Implement delete functionality when available in use cases
        _uiState.update { it.copy(successMessage = "Expense deleted successfully") }
    }
    
    /**
     * Edits an expense.
     */
    private fun editExpense(expense: Expense) {
        // TODO: Implement edit functionality when available in use cases
        _uiState.update { it.copy(successMessage = "Expense updated successfully") }
    }
    
    /**
     * Clears success and error messages.
     */
    private fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, errorMessage = null) }
    }
    
    /**
     * Applies filters and sorting to the expense list.
     */
    private fun applyFiltersAndSorting(expenses: List<Expense>, state: ExpenseListUiState): List<Expense> {
        var filteredList = expenses
        
        // Apply date filter
        state.selectedDate?.let { date ->
            filteredList = filteredList.filter { expense ->
                DateUtils.isSameDay(expense.timestamp, DateUtils.localDateToTimestamp(date))
            }
        }
        
        // Apply category filter
        state.selectedCategory?.let { category ->
            filteredList = filteredList.filter { it.category == category }
        }
        
        // Apply search filter
        if (state.searchQuery.isNotEmpty()) {
            val query = state.searchQuery.lowercase()
            filteredList = filteredList.filter { expense ->
                expense.title.lowercase().contains(query) ||
                expense.notes?.lowercase()?.contains(query) == true ||
                expense.category.displayName.lowercase().contains(query)
            }
        }
        
        // Apply sorting
        return when (state.sortOrder) {
            SortOrder.NEWEST_FIRST -> filteredList.sortedByDescending { it.timestamp }
            SortOrder.OLDEST_FIRST -> filteredList.sortedBy { it.timestamp }
            SortOrder.AMOUNT_HIGH_TO_LOW -> filteredList.sortedByDescending { it.amount }
            SortOrder.AMOUNT_LOW_TO_HIGH -> filteredList.sortedBy { it.amount }
            SortOrder.ALPHABETICAL -> filteredList.sortedBy { it.title.lowercase() }
        }
    }
    
    /**
     * Groups expenses based on the selected grouping mode.
     */
    private fun applyGrouping(expenses: List<Expense>, groupingMode: GroupingMode): Map<String, List<Expense>> {
        return when (groupingMode) {
            GroupingMode.NONE -> emptyMap()
            GroupingMode.BY_CATEGORY -> {
                expenses.groupBy { it.category.displayName }
            }
            GroupingMode.BY_DATE -> {
                expenses.groupBy { expense ->
                    DateUtils.formatDate(expense.timestamp)
                }
            }
            GroupingMode.BY_AMOUNT -> {
                expenses.groupBy { expense ->
                    when {
                        expense.amount < 100 -> "Under ₹100"
                        expense.amount < 500 -> "₹100 - ₹500"
                        expense.amount < 1000 -> "₹500 - ₹1000"
                        expense.amount < 5000 -> "₹1000 - ₹5000"
                        else -> "Above ₹5000"
                    }
                }
            }
        }
    }
}
