// AI-generated: Date extension functions for common date operations in expense tracking
package com.shankarkakumani.common.ext

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Extension functions for Date operations commonly used in expense tracking.
 */

/**
 * Formats the date as "dd MMM yyyy" (e.g., "15 Jan 2024").
 */
fun Date.toDisplayFormat(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(this)
}

/**
 * Formats the date as "dd/MM/yyyy" for compact display.
 */
fun Date.toCompactFormat(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(this)
}

/**
 * Formats the date as "yyyy-MM-dd" for database storage or API calls.
 */
fun Date.toApiFormat(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(this)
}

/**
 * Formats the date and time as "dd MMM yyyy, HH:mm".
 */
fun Date.toDateTimeFormat(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return formatter.format(this)
}

/**
 * Returns true if this date is today.
 */
fun Date.isToday(): Boolean {
    val today = Calendar.getInstance()
    val dateCalendar = Calendar.getInstance().apply { time = this@isToday }
    
    return today.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR) &&
           today.get(Calendar.DAY_OF_YEAR) == dateCalendar.get(Calendar.DAY_OF_YEAR)
}

/**
 * Returns true if this date is in the current week.
 */
fun Date.isThisWeek(): Boolean {
    val today = Calendar.getInstance()
    val dateCalendar = Calendar.getInstance().apply { time = this@isThisWeek }
    
    // Set both calendars to start of week (Monday)
    today.firstDayOfWeek = Calendar.MONDAY
    dateCalendar.firstDayOfWeek = Calendar.MONDAY
    
    return today.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR) &&
           today.get(Calendar.WEEK_OF_YEAR) == dateCalendar.get(Calendar.WEEK_OF_YEAR)
}

/**
 * Returns the start of the day (00:00:00) for this date.
 */
fun Date.startOfDay(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@startOfDay
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.time
}

/**
 * Returns the end of the day (23:59:59.999) for this date.
 */
fun Date.endOfDay(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@endOfDay
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }
    return calendar.time
}

/**
 * Returns the start of the week (Monday 00:00:00) for this date.
 */
fun Date.startOfWeek(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@startOfWeek
        firstDayOfWeek = Calendar.MONDAY
        set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.time
}

/**
 * Returns the end of the week (Sunday 23:59:59.999) for this date.
 */
fun Date.endOfWeek(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@endOfWeek
        firstDayOfWeek = Calendar.MONDAY
        set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }
    return calendar.time
}

/**
 * Adds the specified number of days to this date.
 */
fun Date.addDays(days: Int): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@addDays
        add(Calendar.DAY_OF_MONTH, days)
    }
    return calendar.time
}

/**
 * Subtracts the specified number of days from this date.
 */
fun Date.minusDays(days: Int): Date = addDays(-days)

/**
 * Returns a list of dates for the last 7 days including today.
 */
fun Date.getLast7Days(): List<Date> {
    return (6 downTo 0).map { daysAgo ->
        this.minusDays(daysAgo)
    }
}

/**
 * Parses a string in "yyyy-MM-dd" format to a Date.
 */
fun String.toDateFromApi(): Date? {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formatter.parse(this)
    } catch (e: Exception) {
        null
    }
}
