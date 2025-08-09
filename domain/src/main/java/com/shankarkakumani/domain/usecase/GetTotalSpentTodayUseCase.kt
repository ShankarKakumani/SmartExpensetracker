// AI-generated: Use case for getting today's total spending
package com.shankarkakumani.domain.usecase

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.repository.ExpenseRepository
import javax.inject.Inject

/**
 * Use case for retrieving the total amount spent today.
 * Provides real-time spending information for the current day.
 */
class GetTotalSpentTodayUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    
    /**
     * Gets the total amount spent today.
     * @return Result containing today's total spending amount or error
     */
    suspend operator fun invoke(): Result<Double> {
        return expenseRepository.getTotalSpentToday()
    }
    
    /**
     * Gets the total amount spent for a specific date.
     * @param date The target date timestamp
     * @return Result containing the total spending for the specified date or error
     */
    suspend operator fun invoke(date: Long): Result<Double> {
        return expenseRepository.getTotalSpentOnDate(date)
    }
    
    /**
     * Gets both the total amount and count of expenses for today.
     * @return Result containing TodaySpendingSummary or error
     */
    suspend fun getTodaySpendingSummary(): Result<TodaySpendingSummary> {
        return try {
            val totalResult = expenseRepository.getTotalSpentToday()
            val countResult = expenseRepository.getExpenseCountToday()
            
            when {
                totalResult is Result.Success && countResult is Result.Success -> {
                    Result.Success(
                        TodaySpendingSummary(
                            totalAmount = totalResult.data,
                            expenseCount = countResult.data
                        )
                    )
                }
                totalResult is Result.Error -> totalResult
                countResult is Result.Error -> countResult
                else -> Result.Error(Exception("Unknown error occurred"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    /**
     * Data class containing today's spending summary.
     */
    data class TodaySpendingSummary(
        val totalAmount: Double,
        val expenseCount: Int
    ) {
        /**
         * Returns formatted total amount with currency.
         */
        fun getFormattedTotal(): String = "₹${String.format("%.2f", totalAmount)}"
        
        /**
         * Returns average amount per expense.
         */
        fun getAverageAmount(): Double = if (expenseCount > 0) totalAmount / expenseCount else 0.0
        
        /**
         * Returns formatted average amount with currency.
         */
        fun getFormattedAverage(): String = "₹${String.format("%.2f", getAverageAmount())}"
        
        /**
         * Returns a descriptive summary string.
         */
        fun getSummaryText(): String {
            return if (expenseCount == 0) {
                "No expenses recorded today"
            } else {
                "${getFormattedTotal()} spent across $expenseCount expense${if (expenseCount > 1) "s" else ""}"
            }
        }
    }
}
