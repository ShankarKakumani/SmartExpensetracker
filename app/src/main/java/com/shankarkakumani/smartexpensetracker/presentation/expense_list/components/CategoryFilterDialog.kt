// AI-generated: Stunning category filter dialog with animated selections
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
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme

/**
 * Stunning category filter dialog with gradient design and animations.
 */
@Composable
fun StunningCategoryFilterDialog(
    selectedCategory: ExpenseCategory?,
    onCategorySelected: (ExpenseCategory?) -> Unit,
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
                    text = "Select Category",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Category options
                CategoryOptions(
                    selectedCategory = selectedCategory,
                    onCategorySelected = onCategorySelected,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Action buttons
                CategoryFilterActions(
                    selectedCategory = selectedCategory,
                    onClear = { onCategorySelected(null) },
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
@Composable
private fun CategoryFilterHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.AccountCircle,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = "Select Category",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

/**
 * Category options with animated selections.
 */
@Composable
private fun CategoryOptions(
    selectedCategory: ExpenseCategory?,
    onCategorySelected: (ExpenseCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ExpenseCategory.getAllCategories().forEach { category ->
            AnimatedCategoryOption(
                category = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Individual category option with animation and gradient effects.
 */
@Composable
private fun AnimatedCategoryOption(
    category: ExpenseCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "category_scale_animation"
    )
    
    val categoryIcon = when (category) {
        ExpenseCategory.STAFF -> Icons.Default.Person
        ExpenseCategory.TRAVEL -> Icons.Default.LocationOn
        ExpenseCategory.FOOD -> Icons.Default.ShoppingCart
        ExpenseCategory.UTILITY -> Icons.Default.Settings
    }
    
    val categoryColor = when (category) {
        ExpenseCategory.STAFF -> MaterialTheme.colorScheme.secondary
        ExpenseCategory.TRAVEL -> MaterialTheme.colorScheme.primary
        ExpenseCategory.FOOD -> MaterialTheme.colorScheme.tertiary
        ExpenseCategory.UTILITY -> MaterialTheme.colorScheme.primaryContainer
    }
    
    Card(
        onClick = onClick,
        modifier = modifier
            .scale(animatedScale)
            .shadow(
                elevation = if (isSelected) 12.dp else 4.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = categoryColor.copy(alpha = 0.3f),
                spotColor = categoryColor.copy(alpha = 0.5f)
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
            
            // Category icon with gradient background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = categoryColor.copy(alpha = if (isSelected) 0.25f else 0.15f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    categoryIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Category name and description
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = getCategoryDescription(category),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Selection indicator
            if (isSelected) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = categoryColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

/**
 * Get description for each category.
 */
private fun getCategoryDescription(category: ExpenseCategory): String {
    return when (category) {
        ExpenseCategory.STAFF -> "Employee salaries, benefits, etc."
        ExpenseCategory.TRAVEL -> "Transportation, fuel, accommodation"
        ExpenseCategory.FOOD -> "Meals, refreshments, catering"
        ExpenseCategory.UTILITY -> "Electricity, internet, phone bills"
    }
}

/**
 * Action buttons with gradient styling.
 */
@Composable
private fun CategoryFilterActions(
    selectedCategory: ExpenseCategory?,
    onClear: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onClear) { Text("Clear") }
        Spacer(modifier = Modifier.width(8.dp))
        TextButton(onClick = onConfirm) { Text("Done") }
    }
}

@Preview
@Composable
private fun StunningCategoryFilterDialogPreview() {
    SmartExpenseTrackerTheme {
        StunningCategoryFilterDialog(
            selectedCategory = ExpenseCategory.FOOD,
            onCategorySelected = { },
            onDismiss = { }
        )
    }
}
