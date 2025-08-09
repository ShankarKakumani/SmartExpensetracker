// AI-generated: Stunning date picker dialog with gradient design
package com.shankarkakumani.smartexpensetracker.presentation.expense_list.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.shankarkakumani.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

/**
 * Stunning date picker dialog with gradients and animations.
 */
@Composable
fun StunningDatePickerDialog(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentMonth by remember { mutableStateOf(selectedDate?.let { YearMonth.from(it) } ?: YearMonth.now()) }
    val today = LocalDate.now()
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                
                // Header with gradient text
                DatePickerHeader(
                    currentMonth = currentMonth,
                    onPreviousMonth = { currentMonth = currentMonth.minusMonths(1) },
                    onNextMonth = { currentMonth = currentMonth.plusMonths(1) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Calendar grid with animations
                AnimatedCalendarGrid(
                    currentMonth = currentMonth,
                    selectedDate = selectedDate,
                    today = today,
                    onDateSelected = onDateSelected,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Action buttons with gradient
                DatePickerActions(
                    selectedDate = selectedDate,
                    onClear = { onDateSelected(null) },
                    onToday = { onDateSelected(today) },
                    onConfirm = { onDismiss() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Header with month navigation and gradient styling.
 */
@Suppress("DEPRECATION")
@Composable
private fun DatePickerHeader(
    currentMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        
        // Previous month button
        IconButton(onClick = onPreviousMonth) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous month",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
        }
        
        // Month and year display
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = currentMonth.year.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        // Next month button
        IconButton(onClick = onNextMonth) {
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Next month",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * Animated calendar grid with beautiful day cells.
 */
@Composable
private fun AnimatedCalendarGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    today: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1)
    val startingDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Adjust for Sunday start
    
    Column(modifier = modifier) {
        
        // Day of week headers
        DayOfWeekHeaders(modifier = Modifier.fillMaxWidth())
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Calendar grid
        repeat(6) { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(7) { dayOfWeek ->
                    val dayNumber = week * 7 + dayOfWeek - startingDayOfWeek + 1
                    
                    if (dayNumber in 1..daysInMonth) {
                        val date = currentMonth.atDay(dayNumber)
                        val isSelected = date == selectedDate
                        val isToday = date == today
                        
                        AnimatedDayCell(
                            date = date,
                            isSelected = isSelected,
                            isToday = isToday,
                            onClick = { onDateSelected(date) },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            
            if (week < 5) {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

/**
 * Day of week headers with gradient styling.
 */
@Composable
private fun DayOfWeekHeaders(
    modifier: Modifier = Modifier
) {
    val dayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        dayNames.forEach { dayName ->
            Text(
                text = dayName,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Individual day cell with animation and gradient effects.
 */
@Composable
private fun AnimatedDayCell(
    date: LocalDate,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "day_scale_animation"
    )
    
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        isToday -> MaterialTheme.colorScheme.surfaceVariant
        else -> Color.Transparent
    }
    
    val textColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
        isToday -> MaterialTheme.colorScheme.onSurface
        else -> MaterialTheme.colorScheme.onSurface
    }
    
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .scale(animatedScale)
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}

/**
 * Action buttons with gradient styling.
 */
@Composable
private fun DatePickerActions(
    selectedDate: LocalDate?,
    onClear: () -> Unit,
    onToday: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onClear) { Text("Clear") }
        Spacer(modifier = Modifier.width(8.dp))
        TextButton(onClick = onToday) { Text("Today") }
        Spacer(modifier = Modifier.width(8.dp))
        TextButton(onClick = onConfirm) { Text("Done") }
    }
}

@Preview
@Composable
private fun StunningDatePickerDialogPreview() {
    SmartExpenseTrackerTheme {
        StunningDatePickerDialog(
            selectedDate = LocalDate.now(),
            onDateSelected = { },
            onDismiss = { }
        )
    }
}
