// AI-generated: Comprehensive UI state for expense reports with analytics and charts
package com.shankarkakumani.smartexpensetracker.presentation.expense_report

import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.domain.model.WeeklyReport
import java.time.LocalDate

/**
 * UI state for the expense report screen with comprehensive analytics and chart data.
 */
data class ExpenseReportUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    
    // Weekly report data
    val weeklyReport: WeeklyReport? = null,
    val dailyTotals: List<DayTotal> = emptyList(),
    val categoryBreakdown: List<CategoryTotal> = emptyList(),
    
    // Chart data
    val weeklyChartData: List<ChartEntry> = emptyList(),
    val categoryChartData: List<PieChartEntry> = emptyList(),
    val trendData: List<TrendEntry> = emptyList(),
    
    // Analytics
    val totalSpent: Double = 0.0,
    val dailyAverage: Double = 0.0,
    val highestSpendingDay: DayTotal? = null,
    val topCategory: CategoryTotal? = null,
    val spendingTrend: TrendDirection = TrendDirection.STABLE,
    val weekOverWeekChange: Double = 0.0,
    
    // Insights
    val insights: List<SpendingInsight> = emptyList(),
    val recommendations: List<String> = emptyList(),
    
    // Export
    val isExporting: Boolean = false,
    val exportType: ExportType? = null,
    val showExportDialog: Boolean = false,
    
    // Date range
    val selectedDateRange: DateRange = DateRange.LAST_7_DAYS,
    val customStartDate: LocalDate? = null,
    val customEndDate: LocalDate? = null,
    val showDateRangePicker: Boolean = false
) {
    
    /**
     * Returns formatted total amount with currency.
     */
    fun getFormattedTotal(): String = "₹${String.format("%.2f", totalSpent)}"
    
    /**
     * Returns formatted daily average with currency.
     */
    fun getFormattedDailyAverage(): String = "₹${String.format("%.2f", dailyAverage)}"
    
    /**
     * Returns formatted week-over-week change percentage.
     */
    fun getFormattedWeekChange(): String {
        val sign = if (weekOverWeekChange >= 0) "+" else ""
        return "$sign${String.format("%.1f", weekOverWeekChange)}%"
    }
    
    /**
     * Returns the current period display text.
     */
    fun getPeriodDisplayText(): String {
        return when (selectedDateRange) {
            DateRange.LAST_7_DAYS -> "Last 7 Days"
            DateRange.LAST_30_DAYS -> "Last 30 Days"
            DateRange.THIS_MONTH -> "This Month"
            DateRange.LAST_MONTH -> "Last Month"
            DateRange.CUSTOM -> {
                if (customStartDate != null && customEndDate != null) {
                    "$customStartDate to $customEndDate"
                } else "Custom Range"
            }
        }
    }
    
    /**
     * Returns whether any data is available.
     */
    fun hasData(): Boolean = dailyTotals.isNotEmpty() || categoryBreakdown.isNotEmpty()
    
    /**
     * Returns whether empty state should be shown.
     */
    fun shouldShowEmptyState(): Boolean = !isLoading && !hasData() && errorMessage == null
    
    /**
     * Returns whether charts can be displayed.
     */
    fun canShowCharts(): Boolean = weeklyChartData.isNotEmpty() || categoryChartData.isNotEmpty()
}

/**
 * Represents daily total with date and amount.
 */
data class DayTotal(
    val date: LocalDate,
    val amount: Double,
    val expenseCount: Int
) {
    fun getFormattedAmount(): String = "₹${String.format("%.2f", amount)}"
    fun getDateDisplayText(): String = date.dayOfWeek.name.take(3)
}

/**
 * Represents category total with breakdown.
 */
data class CategoryTotal(
    val category: ExpenseCategory,
    val amount: Double,
    val expenseCount: Int,
    val percentage: Double
) {
    fun getFormattedAmount(): String = "₹${String.format("%.2f", amount)}"
    fun getFormattedPercentage(): String = "${String.format("%.1f", percentage)}%"
}

/**
 * Represents chart entry for line/bar charts.
 */
data class ChartEntry(
    val label: String,
    val value: Float,
    val date: LocalDate? = null
)

/**
 * Represents pie chart entry with color.
 */
data class PieChartEntry(
    val label: String,
    val value: Float,
    val color: Int,
    val category: ExpenseCategory
)

/**
 * Represents trend analysis entry.
 */
data class TrendEntry(
    val period: String,
    val amount: Double,
    val change: Double,
    val changeType: TrendDirection
)

/**
 * Represents spending insights.
 */
data class SpendingInsight(
    val title: String,
    val description: String,
    val type: InsightType,
    val amount: Double? = null,
    val category: ExpenseCategory? = null
)

/**
 * Different types of spending insights.
 */
enum class InsightType {
    HIGH_SPENDING,
    UNUSUAL_PATTERN,
    CATEGORY_ALERT,
    SAVING_OPPORTUNITY,
    POSITIVE_TREND
}

/**
 * Trend direction for analytics.
 */
enum class TrendDirection {
    UP,
    DOWN,
    STABLE
}

/**
 * Export types supported.
 */
enum class ExportType(val displayName: String, val extension: String) {
    PDF("PDF Report", "pdf"),
    CSV("CSV Data", "csv"),
    EXCEL("Excel Spreadsheet", "xlsx")
}

/**
 * Date range options for reports.
 */
enum class DateRange(val displayName: String) {
    LAST_7_DAYS("Last 7 Days"),
    LAST_30_DAYS("Last 30 Days"),
    THIS_MONTH("This Month"),
    LAST_MONTH("Last Month"),
    CUSTOM("Custom Range")
}

/**
 * Actions that can be performed on the expense report screen.
 */
sealed class ExpenseReportAction {
    // Data actions
    object LoadReport : ExpenseReportAction()
    object RefreshReport : ExpenseReportAction()
    object ClearMessages : ExpenseReportAction()
    
    // Date range actions
    data class ChangeDateRange(val range: DateRange) : ExpenseReportAction()
    data class SetCustomDateRange(val startDate: LocalDate, val endDate: LocalDate) : ExpenseReportAction()
    object ToggleDateRangePicker : ExpenseReportAction()
    
    // Export actions
    data class StartExport(val type: ExportType) : ExpenseReportAction()
    object ToggleExportDialog : ExpenseReportAction()
    object CompleteExport : ExpenseReportAction()
    
    // Chart actions
    data class SelectChartEntry(val entry: ChartEntry) : ExpenseReportAction()
    data class SelectCategoryEntry(val entry: PieChartEntry) : ExpenseReportAction()
    
    // Insight actions
    data class DismissInsight(val insight: SpendingInsight) : ExpenseReportAction()
    object GenerateNewInsights : ExpenseReportAction()
}
