// AI-generated: Weekly report model for expense analytics
package com.shankarkakumani.domain.model

/**
 * Represents a weekly expense report with analytics data.
 * Used for generating insights and visualizations.
 */
data class WeeklyReport(
    val weekStartDate: Long, // Start of the week timestamp
    val weekEndDate: Long,   // End of the week timestamp
    val totalAmount: Double,
    val totalExpenses: Int,
    val dailyTotals: List<DayTotal>,
    val categoryBreakdown: Map<ExpenseCategory, CategorySummary>,
    val averageDailySpending: Double
) {
    
    /**
     * Gets the highest spending day in the week.
     */
    fun getHighestSpendingDay(): DayTotal? = dailyTotals.maxByOrNull { it.amount }
    
    /**
     * Gets the most expensive category for the week.
     */
    fun getTopSpendingCategory(): Map.Entry<ExpenseCategory, CategorySummary>? = 
        categoryBreakdown.maxByOrNull { it.value.totalAmount }
    
    /**
     * Calculates percentage of total for a specific category.
     */
    fun getCategoryPercentage(category: ExpenseCategory): Double {
        val categoryAmount = categoryBreakdown[category]?.totalAmount ?: 0.0
        return if (totalAmount > 0) (categoryAmount / totalAmount) * 100 else 0.0
    }
    
    /**
     * Returns formatted total amount with currency.
     */
    fun getFormattedTotal(): String = "₹${String.format("%.2f", totalAmount)}"
    
    /**
     * Returns formatted average daily spending.
     */
    fun getFormattedAverageDaily(): String = "₹${String.format("%.2f", averageDailySpending)}"
}

/**
 * Represents daily spending totals within a week.
 */
data class DayTotal(
    val date: Long,        // Date timestamp (start of day)
    val amount: Double,    // Total spent on this day
    val expenseCount: Int  // Number of expenses on this day
) {
    /**
     * Returns formatted amount with currency.
     */
    fun getFormattedAmount(): String = "₹${String.format("%.2f", amount)}"
}

/**
 * Represents spending summary for a specific category.
 */
data class CategorySummary(
    val category: ExpenseCategory,
    val totalAmount: Double,
    val expenseCount: Int,
    val averageAmount: Double
) {
    /**
     * Returns formatted total amount with currency.
     */
    fun getFormattedTotal(): String = "₹${String.format("%.2f", totalAmount)}"
    
    /**
     * Returns formatted average amount with currency.
     */
    fun getFormattedAverage(): String = "₹${String.format("%.2f", averageAmount)}"
}
