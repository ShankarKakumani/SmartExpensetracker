// AI-generated: Use case for generating weekly expense reports
package com.shankarkakumani.domain.usecase

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.model.WeeklyReport
import com.shankarkakumani.domain.repository.ExpenseRepository
import javax.inject.Inject

/**
 * Use case for generating comprehensive weekly expense reports.
 * Provides analytics and insights for the last 7 days of expenses.
 */
class GetWeeklyReportUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    
    /**
     * Generates a weekly report for the last 7 days.
     * @return Result containing WeeklyReport with analytics or error
     */
    suspend operator fun invoke(): Result<WeeklyReport> {
        return expenseRepository.getWeeklyReport()
    }
    
    /**
     * Gets a simplified weekly summary for quick overview.
     * @return Result containing WeeklySummary or error
     */
    suspend fun getWeeklySummary(): Result<WeeklySummary> {
        return try {
            when (val reportResult = expenseRepository.getWeeklyReport()) {
                is Result.Success -> {
                    val report = reportResult.data
                    Result.Success(
                        WeeklySummary(
                            totalAmount = report.totalAmount,
                            totalExpenses = report.totalExpenses,
                            averageDailySpending = report.averageDailySpending,
                            topCategory = report.getTopSpendingCategory()?.key,
                            topCategoryAmount = report.getTopSpendingCategory()?.value?.totalAmount ?: 0.0,
                            highestSpendingDay = report.getHighestSpendingDay()?.date,
                            highestDayAmount = report.getHighestSpendingDay()?.amount ?: 0.0
                        )
                    )
                }
                is Result.Error -> reportResult
                is Result.Loading -> Result.Loading
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    /**
     * Simplified weekly summary data class.
     * Contains key metrics for quick display.
     */
    data class WeeklySummary(
        val totalAmount: Double,
        val totalExpenses: Int,
        val averageDailySpending: Double,
        val topCategory: com.shankarkakumani.domain.model.ExpenseCategory?,
        val topCategoryAmount: Double,
        val highestSpendingDay: Long?,
        val highestDayAmount: Double
    ) {
        /**
         * Returns formatted total amount with currency.
         */
        fun getFormattedTotal(): String = "₹${String.format("%.2f", totalAmount)}"
        
        /**
         * Returns formatted average daily spending.
         */
        fun getFormattedAverageDaily(): String = "₹${String.format("%.2f", averageDailySpending)}"
        
        /**
         * Returns formatted top category amount.
         */
        fun getFormattedTopCategoryAmount(): String = "₹${String.format("%.2f", topCategoryAmount)}"
        
        /**
         * Returns formatted highest day amount.
         */
        fun getFormattedHighestDayAmount(): String = "₹${String.format("%.2f", highestDayAmount)}"
        
        /**
         * Returns a brief summary text.
         */
        fun getSummaryText(): String {
            return if (totalExpenses == 0) {
                "No expenses recorded this week"
            } else {
                "${getFormattedTotal()} spent across $totalExpenses expense${if (totalExpenses > 1) "s" else ""} this week"
            }
        }
        
        /**
         * Returns insights about spending patterns.
         */
        fun getInsights(): List<String> {
            val insights = mutableListOf<String>()
            
            if (totalExpenses == 0) {
                insights.add("No expenses recorded this week")
                return insights
            }
            
            // Average spending insight
            insights.add("Average daily spending: ${getFormattedAverageDaily()}")
            
            // Top category insight
            topCategory?.let { category ->
                insights.add("Most spending in ${category.displayName}: ${getFormattedTopCategoryAmount()}")
            }
            
            // Highest spending day insight
            if (highestSpendingDay != null) {
                val dayName = com.shankarkakumani.domain.util.DateUtils.getDayName(highestSpendingDay)
                insights.add("Highest spending on $dayName: ${getFormattedHighestDayAmount()}")
            }
            
            return insights
        }
    }
}
