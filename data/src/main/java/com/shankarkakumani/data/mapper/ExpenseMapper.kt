// AI-generated: Mapper for converting between domain and data models
package com.shankarkakumani.data.mapper

import com.shankarkakumani.data.model.ExpenseDto
import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapper class for converting between domain Expense and data ExpenseDto.
 * Handles bidirectional mapping with proper error handling for invalid data.
 */
@Singleton
class ExpenseMapper @Inject constructor() {
    
    /**
     * Converts domain Expense to data ExpenseDto.
     * @param expense Domain expense object
     * @return ExpenseDto for data persistence
     */
    fun toDto(expense: Expense): ExpenseDto {
        return ExpenseDto(
            id = expense.id,
            title = expense.title,
            amount = expense.amount,
            category = expense.category.name, // Convert enum to string
            notes = expense.notes,
            receiptImageUrl = expense.receiptImageUrl,
            timestamp = expense.timestamp
        )
    }
    
    /**
     * Converts data ExpenseDto to domain Expense.
     * @param dto Data transfer object from persistence layer
     * @return Domain expense object
     * @throws IllegalArgumentException if category string is invalid
     */
    fun toDomain(dto: ExpenseDto): Expense {
        val category = ExpenseCategory.fromString(dto.category)
            ?: throw IllegalArgumentException("Invalid expense category: ${dto.category}")
        
        return Expense(
            id = dto.id,
            title = dto.title,
            amount = dto.amount,
            category = category,
            notes = dto.notes,
            receiptImageUrl = dto.receiptImageUrl,
            timestamp = dto.timestamp
        )
    }
    
    /**
     * Converts a list of domain expenses to DTOs.
     * @param expenses List of domain expense objects
     * @return List of ExpenseDto objects
     */
    fun toDtoList(expenses: List<Expense>): List<ExpenseDto> {
        return expenses.map { toDto(it) }
    }
    
    /**
     * Converts a list of DTOs to domain expenses.
     * Filters out any DTOs that fail mapping (invalid categories).
     * @param dtos List of ExpenseDto objects
     * @return List of valid domain expense objects
     */
    fun toDomainList(dtos: List<ExpenseDto>): List<Expense> {
        return dtos.mapNotNull { dto ->
            try {
                toDomain(dto)
            } catch (e: IllegalArgumentException) {
                // Log the error in production, skip invalid entries
                null
            }
        }
    }
    
    /**
     * Safely converts DTO to domain with Result wrapper.
     * @param dto Data transfer object
     * @return Result containing domain object or error
     */
    fun toDomainSafely(dto: ExpenseDto): Result<Expense> {
        return try {
            Result.success(toDomain(dto))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
