// AI-generated: Material 3 shape system for Smart Expense Tracker
package com.shankarkakumani.smartexpensetracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Material 3 shape system customized for expense tracker app.
 * Provides consistent rounded corners and shape tokens.
 */
val Shapes = Shapes(
    // Extra Small - for chips, tags, small buttons
    extraSmall = RoundedCornerShape(4.dp),
    
    // Small - for cards, smaller components
    small = RoundedCornerShape(8.dp),
    
    // Medium - for main cards, containers
    medium = RoundedCornerShape(12.dp),
    
    // Large - for dialogs, large surfaces
    large = RoundedCornerShape(16.dp),
    
    // Extra Large - for bottom sheets, large containers
    extraLarge = RoundedCornerShape(28.dp)
)

/**
 * Additional custom shapes for specific expense tracker components
 */
object ExpenseShapes {
    // Expense cards
    val expenseCard = RoundedCornerShape(12.dp)
    
    // Input fields
    val inputField = RoundedCornerShape(8.dp)
    
    // Buttons
    val button = RoundedCornerShape(24.dp)
    val smallButton = RoundedCornerShape(20.dp)
    
    // Bottom sheets
    val bottomSheet = RoundedCornerShape(
        topStart = 28.dp,
        topEnd = 28.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
    
    // Category chips
    val categoryChip = RoundedCornerShape(16.dp)
    
    // Amount display containers
    val amountContainer = RoundedCornerShape(8.dp)
    
    // Dialog shapes
    val dialog = RoundedCornerShape(28.dp)
    
    // Navigation drawer
    val drawer = RoundedCornerShape(
        topEnd = 16.dp,
        bottomEnd = 16.dp
    )
    
    // Floating action button
    val fab = RoundedCornerShape(16.dp)
    val extendedFab = RoundedCornerShape(16.dp)
    
    // Progress indicators
    val progressIndicator = RoundedCornerShape(4.dp)
}
