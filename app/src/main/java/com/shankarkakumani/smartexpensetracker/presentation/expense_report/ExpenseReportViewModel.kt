// AI-generated: ViewModel for expense reports with comprehensive analytics and chart data processing
package com.shankarkakumani.smartexpensetracker.presentation.expense_report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.domain.usecase.GetExpensesUseCase
import com.shankarkakumani.domain.usecase.GetWeeklyReportUseCase
import com.shankarkakumani.domain.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.abs

/**
 * ViewModel for managing expense report state with analytics, charts, and insights.
 */
@HiltViewModel
class ExpenseReportViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getWeeklyReportUseCase: GetWeeklyReportUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ExpenseReportUiState())
    val uiState: StateFlow<ExpenseReportUiState> = _uiState.asStateFlow()
    
    init {
        loadReport()
    }
    
    /**
     * Handles all user actions for the expense report.
     */
    fun onAction(action: ExpenseReportAction) {
        when (action) {
            is ExpenseReportAction.LoadReport -> loadReport()
            is ExpenseReportAction.RefreshReport -> refreshReport()
            is ExpenseReportAction.ClearMessages -> clearMessages()
            
            is ExpenseReportAction.ChangeDateRange -> changeDateRange(action.range)
            is ExpenseReportAction.SetCustomDateRange -> setCustomDateRange(action.startDate, action.endDate)
            is ExpenseReportAction.ToggleDateRangePicker -> toggleDateRangePicker()
            
            is ExpenseReportAction.StartExport -> startExport(action.type)
            is ExpenseReportAction.ToggleExportDialog -> toggleExportDialog()
            is ExpenseReportAction.CompleteExport -> completeExport()
            
            is ExpenseReportAction.SelectChartEntry -> selectChartEntry(action.entry)
            is ExpenseReportAction.SelectCategoryEntry -> selectCategoryEntry(action.entry)
            
            is ExpenseReportAction.DismissInsight -> dismissInsight(action.insight)
            is ExpenseReportAction.GenerateNewInsights -> generateNewInsights()
        }
    }
    
    /**
     * Loads the expense report data.
     */
    private fun loadReport() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            try {
                // Load weekly report
                when (val weeklyResult = getWeeklyReportUseCase()) {
                    is Result.Success -> {
                        val weeklyReport = weeklyResult.data
                        _uiState.update { it.copy(weeklyReport = weeklyReport) }
                    }
                    is Result.Error -> {
                        _uiState.update { 
                            it.copy(errorMessage = "Failed to load weekly report: ${weeklyResult.exception.message}")
                        }
                    }
                    is Result.Loading -> {
                        // Already showing loading
                    }
                }
                
                // Load detailed expenses for analytics
                getExpensesUseCase.getAllExpenses().collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val expenses = result.data
                            processAnalyticsData(expenses)
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
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = "An error occurred: ${e.message}"
                    )
                }
            }
        }
    }
    
    /**
     * Refreshes the report data.
     */
    private fun refreshReport() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            loadReport()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
    
    /**
     * Processes analytics data from expenses.
     */
    private fun processAnalyticsData(expenses: List<Expense>) {
        val currentState = _uiState.value
        val dateRange = getDateRangeForFilter(currentState.selectedDateRange, currentState.customStartDate, currentState.customEndDate)
        
        // Filter expenses by date range
        val filteredExpenses = expenses.filter { expense ->
            val expenseDate = DateUtils.timestampToLocalDate(expense.timestamp)
            expenseDate in dateRange
        }
        
        // Calculate daily totals
        val dailyTotals = calculateDailyTotals(filteredExpenses, dateRange)
        
        // Calculate category breakdown
        val categoryBreakdown = calculateCategoryBreakdown(filteredExpenses)
        
        // Generate chart data
        val weeklyChartData = generateWeeklyChartData(dailyTotals)
        val categoryChartData = generateCategoryChartData(categoryBreakdown)
        
        // Calculate analytics
        val totalSpent = filteredExpenses.sumOf { it.amount }
        val dayCount = dateRange.endInclusive.toEpochDay() - dateRange.start.toEpochDay() + 1
        val dailyAverage = if (dayCount > 0) totalSpent / dayCount else 0.0
        val highestSpendingDay = dailyTotals.maxByOrNull { it.amount }
        val topCategory = categoryBreakdown.maxByOrNull { it.amount }
        
        // Calculate trend
        val (trendDirection, weekOverWeekChange) = calculateTrend(expenses, filteredExpenses)
        
        // Generate insights
        val insights = generateInsights(filteredExpenses, categoryBreakdown, dailyTotals)
        
        // Generate recommendations
        val recommendations = generateRecommendations(categoryBreakdown, dailyAverage)
        
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = false,
                dailyTotals = dailyTotals,
                categoryBreakdown = categoryBreakdown,
                weeklyChartData = weeklyChartData,
                categoryChartData = categoryChartData,
                totalSpent = totalSpent,
                dailyAverage = dailyAverage,
                highestSpendingDay = highestSpendingDay,
                topCategory = topCategory,
                spendingTrend = trendDirection,
                weekOverWeekChange = weekOverWeekChange,
                insights = insights,
                recommendations = recommendations
            )
        }
    }
    
    /**
     * Calculates daily totals for the given date range.
     */
    private fun calculateDailyTotals(expenses: List<Expense>, dateRange: ClosedRange<LocalDate>): List<DayTotal> {
        val days = mutableListOf<LocalDate>()
        var current = dateRange.start
        while (!current.isAfter(dateRange.endInclusive)) {
            days.add(current)
            current = current.plusDays(1)
        }
        
        return days.map { date ->
            val dayExpenses = expenses.filter { expense ->
                DateUtils.isSameDay(expense.timestamp, DateUtils.localDateToTimestamp(date))
            }
            DayTotal(
                date = date,
                amount = dayExpenses.sumOf { it.amount },
                expenseCount = dayExpenses.size
            )
        }.sortedBy { it.date }
    }
    
    /**
     * Calculates category breakdown with percentages.
     */
    private fun calculateCategoryBreakdown(expenses: List<Expense>): List<CategoryTotal> {
        val totalAmount = expenses.sumOf { it.amount }
        
        return ExpenseCategory.getAllCategories().mapNotNull { category ->
            val categoryExpenses = expenses.filter { it.category == category }
            if (categoryExpenses.isNotEmpty()) {
                val amount = categoryExpenses.sumOf { it.amount }
                CategoryTotal(
                    category = category,
                    amount = amount,
                    expenseCount = categoryExpenses.size,
                    percentage = if (totalAmount > 0) (amount / totalAmount) * 100 else 0.0
                )
            } else null
        }.sortedByDescending { it.amount }
    }
    
    /**
     * Generates chart data for weekly view.
     */
    private fun generateWeeklyChartData(dailyTotals: List<DayTotal>): List<ChartEntry> {
        return dailyTotals.map { dayTotal ->
            ChartEntry(
                label = dayTotal.getDateDisplayText(),
                value = dayTotal.amount.toFloat(),
                date = dayTotal.date
            )
        }
    }
    
    /**
     * Generates pie chart data for categories.
     */
    private fun generateCategoryChartData(categoryBreakdown: List<CategoryTotal>): List<PieChartEntry> {
        val colors = mapOf(
            ExpenseCategory.STAFF to android.graphics.Color.parseColor("#667eea"),
            ExpenseCategory.TRAVEL to android.graphics.Color.parseColor("#764ba2"),
            ExpenseCategory.FOOD to android.graphics.Color.parseColor("#f093fb"),
            ExpenseCategory.UTILITY to android.graphics.Color.parseColor("#4facfe")
        )
        
        return categoryBreakdown.map { categoryTotal ->
            PieChartEntry(
                label = categoryTotal.category.displayName,
                value = categoryTotal.amount.toFloat(),
                color = colors[categoryTotal.category] ?: android.graphics.Color.GRAY,
                category = categoryTotal.category
            )
        }
    }
    
    /**
     * Calculates spending trend and week-over-week change.
     */
    private fun calculateTrend(allExpenses: List<Expense>, currentExpenses: List<Expense>): Pair<TrendDirection, Double> {
        val currentTotal = currentExpenses.sumOf { it.amount }
        
        // Get previous week's expenses for comparison
        val currentStartDate = DateUtils.timestampToLocalDate(currentExpenses.minOfOrNull { it.timestamp } ?: System.currentTimeMillis())
        val previousStartDate = currentStartDate.minusDays(7)
        val previousEndDate = currentStartDate.minusDays(1)
        
        val previousExpenses = allExpenses.filter { expense ->
            val expenseDate = DateUtils.timestampToLocalDate(expense.timestamp)
            expenseDate >= previousStartDate && expenseDate <= previousEndDate
        }
        
        val previousTotal = previousExpenses.sumOf { it.amount }
        
        val change = if (previousTotal > 0) {
            ((currentTotal - previousTotal) / previousTotal) * 100
        } else if (currentTotal > 0) {
            100.0 // 100% increase from 0
        } else {
            0.0
        }
        
        val direction = when {
            abs(change) < 5 -> TrendDirection.STABLE
            change > 0 -> TrendDirection.UP
            else -> TrendDirection.DOWN
        }
        
        return direction to change
    }
    
    /**
     * Generates spending insights based on data analysis.
     */
    private fun generateInsights(
        expenses: List<Expense>,
        categoryBreakdown: List<CategoryTotal>,
        dailyTotals: List<DayTotal>
    ): List<SpendingInsight> {
        val insights = mutableListOf<SpendingInsight>()
        
        // High spending day insight
        val highestDay = dailyTotals.maxByOrNull { it.amount }
        if (highestDay != null && highestDay.amount > 0) {
            insights.add(
                SpendingInsight(
                    title = "Highest Spending Day",
                    description = "You spent ${highestDay.getFormattedAmount()} on ${highestDay.date.dayOfWeek.name}",
                    type = InsightType.HIGH_SPENDING,
                    amount = highestDay.amount
                )
            )
        }
        
        // Category dominance insight
        val topCategory = categoryBreakdown.firstOrNull()
        if (topCategory != null && topCategory.percentage > 50) {
            insights.add(
                SpendingInsight(
                    title = "Category Alert",
                    description = "${topCategory.category.displayName} expenses make up ${topCategory.getFormattedPercentage()} of your spending",
                    type = InsightType.CATEGORY_ALERT,
                    amount = topCategory.amount,
                    category = topCategory.category
                )
            )
        }
        
        // Saving opportunity insight
        val foodCategory = categoryBreakdown.find { it.category == ExpenseCategory.FOOD }
        if (foodCategory != null && foodCategory.amount > dailyTotals.sumOf { it.amount } * 0.3) {
            insights.add(
                SpendingInsight(
                    title = "Saving Opportunity",
                    description = "Consider meal planning to reduce food expenses",
                    type = InsightType.SAVING_OPPORTUNITY,
                    category = ExpenseCategory.FOOD
                )
            )
        }
        
        return insights
    }
    
    /**
     * Generates recommendations based on spending patterns.
     */
    private fun generateRecommendations(categoryBreakdown: List<CategoryTotal>, dailyAverage: Double): List<String> {
        val recommendations = mutableListOf<String>()
        
        if (dailyAverage > 1000) {
            recommendations.add("Consider setting a daily spending limit to control expenses")
        }
        
        val topCategory = categoryBreakdown.firstOrNull()
        if (topCategory != null && topCategory.percentage > 40) {
            recommendations.add("Try to diversify your spending across different categories")
        }
        
        if (categoryBreakdown.any { it.category == ExpenseCategory.UTILITY && it.percentage > 30 }) {
            recommendations.add("Look for ways to reduce utility costs, such as energy-efficient appliances")
        }
        
        recommendations.add("Track your expenses daily for better financial awareness")
        
        return recommendations
    }
    
    /**
     * Gets date range based on selected option.
     */
    private fun getDateRangeForFilter(
        dateRange: DateRange,
        customStart: LocalDate?,
        customEnd: LocalDate?
    ): ClosedRange<LocalDate> {
        val today = LocalDate.now()
        return when (dateRange) {
            DateRange.LAST_7_DAYS -> today.minusDays(6)..today
            DateRange.LAST_30_DAYS -> today.minusDays(29)..today
            DateRange.THIS_MONTH -> today.withDayOfMonth(1)..today
            DateRange.LAST_MONTH -> {
                val lastMonth = today.minusMonths(1)
                lastMonth.withDayOfMonth(1)..lastMonth.withDayOfMonth(lastMonth.lengthOfMonth())
            }
            DateRange.CUSTOM -> {
                if (customStart != null && customEnd != null) {
                    customStart..customEnd
                } else {
                    today.minusDays(6)..today
                }
            }
        }
    }
    
    // Action handlers
    private fun changeDateRange(range: DateRange) {
        _uiState.update { it.copy(selectedDateRange = range, showDateRangePicker = false) }
        loadReport()
    }
    
    private fun setCustomDateRange(startDate: LocalDate, endDate: LocalDate) {
        _uiState.update { 
            it.copy(
                selectedDateRange = DateRange.CUSTOM,
                customStartDate = startDate,
                customEndDate = endDate,
                showDateRangePicker = false
            )
        }
        loadReport()
    }
    
    private fun toggleDateRangePicker() {
        _uiState.update { it.copy(showDateRangePicker = !it.showDateRangePicker) }
    }
    
    private fun startExport(type: ExportType) {
        viewModelScope.launch {
            _uiState.update { it.copy(isExporting = true, exportType = type, showExportDialog = false) }
            
            // Simulate export process
            kotlinx.coroutines.delay(2000)
            
            _uiState.update { 
                it.copy(
                    isExporting = false,
                    successMessage = "Report exported successfully as ${type.displayName}"
                )
            }
        }
    }
    
    private fun toggleExportDialog() {
        _uiState.update { it.copy(showExportDialog = !it.showExportDialog) }
    }
    
    private fun completeExport() {
        _uiState.update { it.copy(isExporting = false, exportType = null) }
    }
    
    private fun selectChartEntry(entry: ChartEntry) {
        // Handle chart entry selection - could show detailed view
    }
    
    private fun selectCategoryEntry(entry: PieChartEntry) {
        // Handle category entry selection - could filter by category
    }
    
    private fun dismissInsight(insight: SpendingInsight) {
        _uiState.update { 
            it.copy(insights = it.insights.filter { existingInsight -> existingInsight != insight })
        }
    }
    
    private fun generateNewInsights() {
        loadReport() // Regenerate insights
    }
    
    private fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, errorMessage = null) }
    }
}
