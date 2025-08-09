// AI-generated: In-memory data source for expense management
package com.shankarkakumani.data.datasource

import com.shankarkakumani.data.model.ExpenseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * In-memory data source for expense management.
 * Provides thread-safe CRUD operations with mock data for testing and development.
 * Uses ConcurrentHashMap and Mutex for thread safety.
 */
@Singleton
class ExpenseInMemoryDataSource @Inject constructor() : ExpenseLocalDataSource {
    
    // Thread-safe storage
    private val expenses = ConcurrentHashMap<String, ExpenseDto>()
    private val mutex = Mutex()
    
    // Reactive data stream
    private val _expensesFlow = MutableStateFlow<List<ExpenseDto>>(emptyList())
    override val expensesFlow: Flow<List<ExpenseDto>> = _expensesFlow.asStateFlow()
    
    init {
        // Initialize with mock data
        initializeMockData()
    }
    
    /**
     * Adds a new expense to the data source.
     * @param expense The expense to add
     * @return The added expense with confirmed ID
     */
    override suspend fun addExpense(expense: ExpenseDto): ExpenseDto = mutex.withLock {
        val expenseWithId = if (expense.id.isBlank()) {
            expense.copy(id = UUID.randomUUID().toString())
        } else {
            expense
        }
        
        expenses[expenseWithId.id] = expenseWithId
        updateFlow()
        return expenseWithId
    }
    
    /**
     * Retrieves all expenses.
     * @return List of all expenses
     */
    override suspend fun getAllExpenses(): List<ExpenseDto> = mutex.withLock {
        expenses.values.sortedByDescending { it.timestamp }
    }
    
    /**
     * Retrieves expenses by date range.
     * @param startDate Start timestamp (inclusive)
     * @param endDate End timestamp (inclusive)
     * @return Filtered list of expenses
     */
    override suspend fun getExpensesByDateRange(startDate: Long, endDate: Long): List<ExpenseDto> = mutex.withLock {
        expenses.values
            .filter { it.timestamp in startDate..endDate }
            .sortedByDescending { it.timestamp }
    }
    
    /**
     * Retrieves expenses by category.
     * @param category The category string to filter by
     * @return Filtered list of expenses
     */
    override suspend fun getExpensesByCategory(category: String): List<ExpenseDto> = mutex.withLock {
        expenses.values
            .filter { it.category.equals(category, ignoreCase = true) }
            .sortedByDescending { it.timestamp }
    }
    
    /**
     * Retrieves expenses by both date range and category.
     * @param startDate Start timestamp (inclusive)
     * @param endDate End timestamp (inclusive)
     * @param category The category string to filter by
     * @return Filtered list of expenses
     */
    override suspend fun getExpensesByDateRangeAndCategory(
        startDate: Long,
        endDate: Long,
        category: String
    ): List<ExpenseDto> = mutex.withLock {
        expenses.values
            .filter { 
                it.timestamp in startDate..endDate && 
                it.category.equals(category, ignoreCase = true) 
            }
            .sortedByDescending { it.timestamp }
    }
    
    /**
     * Updates an existing expense.
     * @param expense The expense to update
     * @return The updated expense or null if not found
     */
    override suspend fun updateExpense(expense: ExpenseDto): ExpenseDto? = mutex.withLock {
        if (expenses.containsKey(expense.id)) {
            expenses[expense.id] = expense
            updateFlow()
            expense
        } else {
            null
        }
    }
    
    /**
     * Deletes an expense by ID.
     * @param expenseId The ID of the expense to delete
     * @return True if deleted, false if not found
     */
    override suspend fun deleteExpense(expenseId: String): Boolean = mutex.withLock {
        val removed = expenses.remove(expenseId) != null
        if (removed) {
            updateFlow()
        }
        removed
    }
    
    /**
     * Searches expenses by title or notes.
     * @param query The search query
     * @return List of matching expenses
     */
    override suspend fun searchExpenses(query: String): List<ExpenseDto> = mutex.withLock {
        if (query.isBlank()) return emptyList()
        
        expenses.values
            .filter { expense ->
                expense.title.contains(query, ignoreCase = true) ||
                expense.notes?.contains(query, ignoreCase = true) == true
            }
            .sortedByDescending { it.timestamp }
    }
    
    /**
     * Gets the count of expenses in a date range.
     * @param startDate Start timestamp (inclusive)
     * @param endDate End timestamp (inclusive)
     * @return Count of expenses in the range
     */
    override suspend fun getExpenseCountByDateRange(startDate: Long, endDate: Long): Int = mutex.withLock {
        expenses.values.count { it.timestamp in startDate..endDate }
    }
    
    /**
     * Clears all expenses (useful for testing).
     */
    override suspend fun clearAllExpenses() = mutex.withLock {
        expenses.clear()
        updateFlow()
    }
    
    /**
     * Updates the reactive flow with current expenses.
     */
    private fun updateFlow() {
        _expensesFlow.value = expenses.values.sortedByDescending { it.timestamp }
    }
    
    /**
     * Initializes the data source with realistic mock data.
     * Creates expenses across different categories and dates for testing.
     */
    private fun initializeMockData() {
        val calendar = Calendar.getInstance()
        val mockExpenses = mutableListOf<ExpenseDto>()
        
        // Generate expenses for the last 7 days
        for (dayOffset in 0..6) {
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.DAY_OF_YEAR, -dayOffset)
            
            val dayStart = calendar.apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            
            // Add 1-4 random expenses per day
            val expensesPerDay = (1..4).random()
            
            repeat(expensesPerDay) { expenseIndex ->
                val randomHour = (8..20).random()
                val randomMinute = (0..59).random()
                
                calendar.timeInMillis = dayStart
                calendar.set(Calendar.HOUR_OF_DAY, randomHour)
                calendar.set(Calendar.MINUTE, randomMinute)
                
                val expense = generateMockExpense(calendar.timeInMillis, dayOffset, expenseIndex)
                mockExpenses.add(expense)
            }
        }
        
        // Add all mock expenses to storage
        mockExpenses.forEach { expense ->
            expenses[expense.id] = expense
        }
        
        updateFlow()
    }
    
    /**
     * Generates a realistic mock expense.
     */
    private fun generateMockExpense(timestamp: Long, dayOffset: Int, expenseIndex: Int): ExpenseDto {
        val categories = listOf("STAFF", "TRAVEL", "FOOD", "UTILITY")
        val category = categories.random()
        
        val (title, amount, notes) = when (category) {
            "STAFF" -> Triple(
                listOf("Employee Lunch", "Office Supplies", "Staff Training", "Team Meeting").random(),
                (150.0..2500.0).random(),
                listOf("Training session", "Monthly supplies", "Team building", null).random()
            )
            "TRAVEL" -> Triple(
                listOf("Client Visit", "Taxi Fare", "Fuel Expense", "Parking Fee").random(),
                (50.0..1200.0).random(),
                listOf("Client meeting", "Business trip", "Daily commute", null).random()
            )
            "FOOD" -> Triple(
                listOf("Office Lunch", "Coffee Meeting", "Team Dinner", "Snacks").random(),
                (25.0..800.0).random(),
                listOf("Business lunch", "Client meeting", "Office snacks", null).random()
            )
            "UTILITY" -> Triple(
                listOf("Internet Bill", "Phone Bill", "Electricity", "Office Rent").random(),
                (200.0..5000.0).random(),
                listOf("Monthly bill", "Office utilities", "Communication", null).random()
            )
            else -> Triple("General Expense", 100.0, null)
        }
        
        return ExpenseDto(
            id = UUID.randomUUID().toString(),
            title = title,
            amount = amount,
            category = category,
            notes = notes,
            receiptImageUrl = if ((0..10).random() > 7) "mock://receipt_${UUID.randomUUID()}.jpg" else null,
            timestamp = timestamp
        )
    }
}

/**
 * Extension function to generate random Double in range.
 */
private fun ClosedRange<Double>.random(): Double {
    return start + (endInclusive - start) * kotlin.random.Random.nextDouble()
}
