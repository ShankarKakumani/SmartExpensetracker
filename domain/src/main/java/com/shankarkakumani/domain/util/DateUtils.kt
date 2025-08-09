// AI-generated: Date utility functions for expense tracking and filtering
package com.shankarkakumani.domain.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility object for date-related operations in the expense tracker.
 * Provides functions for date formatting, filtering, and calculations.
 */
object DateUtils {
    
    // Date format patterns
    private const val DATE_FORMAT_DISPLAY = "dd MMM yyyy"
    private const val DATE_FORMAT_SHORT = "dd/MM/yyyy"
    private const val TIME_FORMAT_DISPLAY = "HH:mm"
    private const val DATETIME_FORMAT_FULL = "dd MMM yyyy, HH:mm"
    
    /**
     * Gets the start of today (00:00:00).
     */
    fun getStartOfToday(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
    
    /**
     * Gets the end of today (23:59:59).
     */
    fun getEndOfToday(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }
    
    /**
     * Gets the start of a specific date (00:00:00).
     */
    fun getStartOfDate(timestamp: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
    
    /**
     * Gets the end of a specific date (23:59:59).
     */
    fun getEndOfDate(timestamp: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }
    
    /**
     * Gets the start of the current week (Monday 00:00:00).
     */
    fun getStartOfWeek(): Long {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
    
    /**
     * Gets the end of the current week (Sunday 23:59:59).
     */
    fun getEndOfWeek(): Long {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }
    
    /**
     * Gets timestamps for the last 7 days including today.
     * Returns list of (startOfDay, endOfDay) pairs.
     */
    fun getLast7DaysRange(): List<Pair<Long, Long>> {
        val ranges = mutableListOf<Pair<Long, Long>>()
        val calendar = Calendar.getInstance()
        
        for (i in 0..6) {
            val startOfDay = getStartOfDate(calendar.timeInMillis)
            val endOfDay = getEndOfDate(calendar.timeInMillis)
            ranges.add(startOfDay to endOfDay)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }
        
        return ranges.reversed() // Return in chronological order
    }
    
    /**
     * Checks if a timestamp is today.
     */
    fun isToday(timestamp: Long): Boolean {
        val today = getStartOfToday()
        val endToday = getEndOfToday()
        return timestamp in today..endToday
    }
    
    /**
     * Checks if a timestamp is within the current week.
     */
    fun isThisWeek(timestamp: Long): Boolean {
        val weekStart = getStartOfWeek()
        val weekEnd = getEndOfWeek()
        return timestamp in weekStart..weekEnd
    }
    
    /**
     * Checks if two timestamps are on the same day.
     */
    fun isSameDay(timestamp1: Long, timestamp2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = timestamp1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = timestamp2 }
        
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
    
    /**
     * Formats timestamp to display date (e.g., "25 Dec 2023").
     */
    fun formatDate(timestamp: Long): String {
        val formatter = SimpleDateFormat(DATE_FORMAT_DISPLAY, Locale.getDefault())
        return formatter.format(Date(timestamp))
    }
    
    /**
     * Formats timestamp to short date (e.g., "25/12/2023").
     */
    fun formatDateShort(timestamp: Long): String {
        val formatter = SimpleDateFormat(DATE_FORMAT_SHORT, Locale.getDefault())
        return formatter.format(Date(timestamp))
    }
    
    /**
     * Formats timestamp to time (e.g., "14:30").
     */
    fun formatTime(timestamp: Long): String {
        val formatter = SimpleDateFormat(TIME_FORMAT_DISPLAY, Locale.getDefault())
        return formatter.format(Date(timestamp))
    }
    
    /**
     * Formats timestamp to full date and time (e.g., "25 Dec 2023, 14:30").
     */
    fun formatDateTime(timestamp: Long): String {
        val formatter = SimpleDateFormat(DATETIME_FORMAT_FULL, Locale.getDefault())
        return formatter.format(Date(timestamp))
    }
    
    /**
     * Returns a relative date string (e.g., "Today", "Yesterday", or formatted date).
     */
    fun getRelativeDateString(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        val today = getStartOfToday()
        val yesterday = today - 24 * 60 * 60 * 1000
        
        return when {
            timestamp >= today -> "Today"
            timestamp >= yesterday -> "Yesterday"
            else -> formatDate(timestamp)
        }
    }
    
    /**
     * Gets the day name for a timestamp (e.g., "Monday").
     */
    fun getDayName(timestamp: Long): String {
        val formatter = SimpleDateFormat("EEEE", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }
    
    /**
     * Gets day of month from timestamp.
     */
    fun getDayOfMonth(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar.get(Calendar.DAY_OF_MONTH)
    }
    
    /**
     * Converts LocalDate to timestamp.
     */
    fun localDateToTimestamp(localDate: java.time.LocalDate): Long {
        return localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
    
    /**
     * Converts timestamp to LocalDate.
     */
    fun timestampToLocalDate(timestamp: Long): java.time.LocalDate {
        return java.time.Instant.ofEpochMilli(timestamp)
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate()
    }
}
