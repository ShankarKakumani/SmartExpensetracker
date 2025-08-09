// AI-generated: Stunning grouping options dialog with gradient design
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.shankarkakumani.smartexpensetracker.presentation.expense_list.GroupingMode
import com.shankarkakumani.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme

/**
 * Stunning grouping options dialog with gradient design and animations.
 */
@Composable
fun StunningGroupingOptionsDialog(
    selectedGrouping: GroupingMode,
    onGroupingSelected: (GroupingMode) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    .padding(24.dp)
            ) {
                
                // Header
                Text(
                    text = "Group Expenses",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Grouping options
                GroupingOptions(
                    selectedGrouping = selectedGrouping,
                    onGroupingSelected = onGroupingSelected,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Action buttons
                GroupingOptionsActions(
                    selectedGrouping = selectedGrouping,
                    onConfirm = { onDismiss() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Header with gradient text and icon.
 */
@Suppress("DEPRECATION")
@Composable
private fun GroupingOptionsHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.List,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = "Group Expenses",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

/**
 * Grouping options with animated selections.
 */
@Composable
private fun GroupingOptions(
    selectedGrouping: GroupingMode,
    onGroupingSelected: (GroupingMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GroupingMode.getAllModes().forEach { groupingMode ->
            AnimatedGroupingOption(
                groupingMode = groupingMode,
                isSelected = groupingMode == selectedGrouping,
                onClick = { onGroupingSelected(groupingMode) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Individual grouping option with animation and gradient effects.
 */
@Suppress("DEPRECATION")
@Composable
private fun AnimatedGroupingOption(
    groupingMode: GroupingMode,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "grouping_scale_animation"
    )
    
    val groupingIcon = when (groupingMode) {
        GroupingMode.NONE -> Icons.Default.List
        GroupingMode.BY_CATEGORY -> Icons.Default.AccountCircle
        GroupingMode.BY_DATE -> Icons.Default.DateRange
        GroupingMode.BY_AMOUNT -> Icons.Default.Star
    }
    
    val groupingColor = when (groupingMode) {
        GroupingMode.NONE -> MaterialTheme.colorScheme.onSurfaceVariant
        GroupingMode.BY_CATEGORY -> MaterialTheme.colorScheme.secondary
        GroupingMode.BY_DATE -> MaterialTheme.colorScheme.primary
        GroupingMode.BY_AMOUNT -> MaterialTheme.colorScheme.tertiary
    }
    
    Card(
        onClick = onClick,
        modifier = modifier
            .scale(animatedScale)
            .shadow(
                elevation = if (isSelected) 12.dp else 4.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = groupingColor.copy(alpha = 0.3f),
                spotColor = groupingColor.copy(alpha = 0.5f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            
            // Grouping icon with gradient background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = groupingColor.copy(alpha = if (isSelected) 0.25f else 0.15f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    groupingIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Grouping name and description
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = groupingMode.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = getGroupingDescription(groupingMode),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Selection indicator
            if (isSelected) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = groupingColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

/**
 * Get description for each grouping mode.
 */
private fun getGroupingDescription(groupingMode: GroupingMode): String {
    return when (groupingMode) {
        GroupingMode.NONE -> "Show all expenses in a single list"
        GroupingMode.BY_CATEGORY -> "Group expenses by category type"
        GroupingMode.BY_DATE -> "Group expenses by date"
        GroupingMode.BY_AMOUNT -> "Group expenses by amount ranges"
    }
}

/**
 * Action buttons with gradient styling.
 */
@Composable
private fun GroupingOptionsActions(
    selectedGrouping: GroupingMode,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onConfirm) { Text("Done") }
    }
}

@Preview
@Composable
private fun StunningGroupingOptionsDialogPreview() {
    SmartExpenseTrackerTheme {
        StunningGroupingOptionsDialog(
            selectedGrouping = GroupingMode.BY_CATEGORY,
            onGroupingSelected = { },
            onDismiss = { }
        )
    }
}
