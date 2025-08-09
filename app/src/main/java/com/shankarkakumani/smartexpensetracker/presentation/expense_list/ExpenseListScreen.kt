// AI-generated: Stunning expense list screen with gradients, animations and modern UI
package com.shankarkakumani.smartexpensetracker.presentation.expense_list

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.platform.LocalDensity
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shankarkakumani.domain.model.Expense
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.smartexpensetracker.presentation.expense_list.components.StunningDatePickerDialog
import com.shankarkakumani.smartexpensetracker.presentation.expense_list.components.StunningCategoryFilterDialog
import com.shankarkakumani.smartexpensetracker.presentation.expense_list.components.StunningGroupingOptionsDialog
import com.shankarkakumani.smartexpensetracker.ui.components.ExpenseListSkeleton
import com.shankarkakumani.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme
// import removed: AnimatedFloatingActionButton no longer used after removing FAB
import java.time.LocalDate
import kotlin.math.cos
import kotlin.math.sin

/**
 * Main expense list screen with stunning gradients and animations.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExpenseListScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpenseListViewModel = hiltViewModel(),
    onNavigateToAddExpense: () -> Unit = {},
    onNavigateToExpenseDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    // Handle messages
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.onAction(ExpenseListAction.ClearMessages)
        }
    }
    
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.onAction(ExpenseListAction.ClearMessages)
        }
    }
    
    ExpenseListContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateToAddExpense = onNavigateToAddExpense,
        onNavigateToExpenseDetail = onNavigateToExpenseDetail,
        modifier = modifier
    )
}

/**
 * Main content with stunning gradients and animations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpenseListContent(
    uiState: ExpenseListUiState,
    onAction: (ExpenseListAction) -> Unit,
    onNavigateToAddExpense: () -> Unit,
    onNavigateToExpenseDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            
            // Stunning header with glassmorphism effect
            GlassmorphicHeader(
                uiState = uiState,
                onAction = onAction,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Main content area
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when {
                    uiState.isLoading -> {
                        // AI-generated: Use standardized skeleton loader
                        ExpenseListSkeleton(modifier = Modifier.fillMaxSize())
                    }
                    uiState.shouldShowEmptyState() -> {
                        StunningEmptyState(
                            onAddExpense = onNavigateToAddExpense,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    uiState.shouldShowContent() -> {
                        ExpenseListMainContent(
                            uiState = uiState,
                            onAction = onAction,
                            onNavigateToExpenseDetail = onNavigateToExpenseDetail,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                
                // AI-generated: FAB removed per design update for cleaner list view
            }
        }
        
        // Dialogs
        if (uiState.showDatePicker) {
            StunningDatePickerDialog(
                selectedDate = uiState.selectedDate,
                onDateSelected = { onAction(ExpenseListAction.SelectDate(it)) },
                onDismiss = { onAction(ExpenseListAction.ToggleDatePicker) }
            )
        }
        
        if (uiState.showCategoryFilter) {
            StunningCategoryFilterDialog(
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { onAction(ExpenseListAction.SelectCategory(it)) },
                onDismiss = { onAction(ExpenseListAction.ToggleCategoryFilter) }
            )
        }
        
        if (uiState.showGroupingOptions) {
            StunningGroupingOptionsDialog(
                selectedGrouping = uiState.groupingMode,
                onGroupingSelected = { onAction(ExpenseListAction.ChangeGroupingMode(it)) },
                onDismiss = { onAction(ExpenseListAction.ToggleGroupingOptions) }
            )
        }
    }
}

/**
 * Glassmorphic header with filters and summary.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GlassmorphicHeader(
    uiState: ExpenseListUiState,
    onAction: (ExpenseListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            
            // Title and summary with animated counters
            AnimatedSummarySection(
                uiState = uiState,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Search bar with gradient border
            GradientSearchBar(
                query = uiState.searchQuery,
                onQueryChange = { onAction(ExpenseListAction.UpdateSearchQuery(it)) },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Filter chips with neon effects
            NeonFilterChips(
                uiState = uiState,
                onAction = onAction,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Animated summary section with counters.
 */
@Composable
private fun AnimatedSummarySection(
    uiState: ExpenseListUiState,
    modifier: Modifier = Modifier
) {
    val animatedTotal by animateFloatAsState(
        targetValue = uiState.totalAmount.toFloat(),
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "total_animation"
    )
    
    val animatedCount by animateIntAsState(
        targetValue = uiState.totalExpenses,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "count_animation"
    )
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Title + meta
        Column(modifier = Modifier.weight(1f)) {
            val titleText = if (uiState.selectedDate != null) "Expenses" else uiState.getScreenTitle()
            Text(
                text = titleText,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (uiState.selectedDate != null) {
                Text(
                    text = uiState.selectedDate.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = "$animatedCount expenses",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Right: Total amount + filter status
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "₹${String.format("%.2f", animatedTotal)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )

            if (uiState.hasActiveFilters()) {
                Text(
                    text = "Filtered",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

/**
 * Gradient search bar with glow effect.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GradientSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { 
            Text(
                "Search expenses...", 
                // AI-generated: Use theme placeholder color for readability in light mode
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ) 
        },
        leadingIcon = { 
            Icon(
                Icons.Default.Search, 
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            ) 
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Clear search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            // AI-generated: Align with AddExpense screen text field colors
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
    )
}

/**
 * Neon filter chips with glow effects.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Suppress("DEPRECATION")
@Composable
private fun NeonFilterChips(
    uiState: ExpenseListUiState,
    onAction: (ExpenseListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        
        // Date filter chip
        item {
            NeonFilterChip(
                selected = uiState.selectedDate != null,
                onClick = { onAction(ExpenseListAction.ToggleDatePicker) },
                label = uiState.selectedDate?.toString() ?: "Date",
                icon = Icons.Default.DateRange,
                glowColor = Color(0xFF00CED1) // Dark turquoise
            )
        }
        
        // Category filter chip
        item {
            NeonFilterChip(
                selected = uiState.selectedCategory != null,
                onClick = { onAction(ExpenseListAction.ToggleCategoryFilter) },
                label = uiState.selectedCategory?.displayName ?: "Category",
                icon = Icons.Default.AccountCircle,
                glowColor = Color(0xFFFF69B4) // Hot pink
            )
        }
        
        // Grouping chip
        item {
            NeonFilterChip(
                selected = uiState.groupingMode != GroupingMode.NONE,
                onClick = { onAction(ExpenseListAction.ToggleGroupingOptions) },
                label = uiState.groupingMode.displayName,
                icon = Icons.Default.List,
                glowColor = Color(0xFF9370DB) // Medium purple
            )
        }
        
            // AI-generated: Sort chip removed (feature disabled)
        
        // Clear filters chip
        if (uiState.hasActiveFilters()) {
            item {
                NeonFilterChip(
                    selected = false,
                    onClick = { onAction(ExpenseListAction.ClearAllFilters) },
                    label = "Clear",
                    icon = Icons.Default.Clear,
                    glowColor = Color(0xFFFF6347) // Tomato
                )
            }
        }
    }
}

/**
 * Individual neon filter chip with glow effect.
 */
@Composable
private fun NeonFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    glowColor: Color,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val animatedGlow by animateFloatAsState(
        targetValue = if (selected) 1f else 0.3f,
        animationSpec = tween(300),
        label = "glow_animation"
    )
    
    Card(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick()
        },
        colors = CardDefaults.cardColors(
            // AI-generated: Use Material theme containers for contrast
            containerColor = if (selected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .shadow(
                elevation = if (selected) 8.dp else 2.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = glowColor.copy(alpha = animatedGlow * 0.5f),
                spotColor = glowColor.copy(alpha = animatedGlow * 0.7f)
            )
            .semantics { contentDescription = "Filter: $label" }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                icon, 
                contentDescription = label,
                tint = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
            
            Text(
                label, 
                color = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

/**
 * Main expense list content with cards.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ExpenseListMainContent(
    uiState: ExpenseListUiState,
    onAction: (ExpenseListAction) -> Unit,
    onNavigateToExpenseDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 88.dp) // Space for FAB
    ) {
        
        if (uiState.groupingMode == GroupingMode.NONE) {
            // Regular list view
            items(
                items = uiState.filteredExpenses,
                key = { it.id }
            ) { expense ->
                StunningExpenseCard(
                    expense = expense,
                    onClick = { onNavigateToExpenseDetail(expense.id) },
                    modifier = Modifier.animateItem()
                )
            }
        } else {
            // Grouped view
            uiState.groupedExpenses.forEach { (groupTitle, expenses) ->
                item(key = "group_$groupTitle") {
                    GroupHeaderCard(
                        title = groupTitle,
                        count = expenses.size,
                        total = expenses.sumOf { it.amount },
                        modifier = Modifier.animateItem()
                    )
                }
                
                items(
                    items = expenses,
                    key = { "${groupTitle}_${it.id}" }
                ) { expense ->
                    StunningExpenseCard(
                        expense = expense,
                        onClick = { onNavigateToExpenseDetail(expense.id) },
                        modifier = Modifier
                            .animateItem()
                            .padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

/**
 * Stunning expense card with gradient and animations.
 */
@Composable
private fun StunningExpenseCard(
    expense: Expense,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColor = when (expense.category) {
        ExpenseCategory.STAFF -> Color(0xFF667eea)
        ExpenseCategory.TRAVEL -> Color(0xFF764ba2)
        ExpenseCategory.FOOD -> Color(0xFFf093fb)
        ExpenseCategory.UTILITY -> Color(0xFF4facfe)
    }
    
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = categoryColor.copy(alpha = 0.3f),
                spotColor = categoryColor.copy(alpha = 0.5f)
            ),
        colors = CardDefaults.cardColors(
            // AI-generated: Use theme surface to respect light/dark mode
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            
            // Left section with icon and details
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // AI-generated: Show receipt thumbnail if available; otherwise show category icon
                if (expense.receiptImageUrl != null) {
                    val receiptBitmap = remember(expense.receiptImageUrl) {
                        try { BitmapFactory.decodeFile(expense.receiptImageUrl)?.asImageBitmap() } catch (e: Exception) { null }
                    }
                    if (receiptBitmap != null) {
                        Image(
                            bitmap = receiptBitmap,
                            contentDescription = "Receipt thumbnail",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            categoryColor,
                                            categoryColor.copy(alpha = 0.7f)
                                        )
                                    ),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (expense.category) {
                                    ExpenseCategory.STAFF -> Icons.Default.Person
                                    ExpenseCategory.TRAVEL -> Icons.Default.LocationOn
                                    ExpenseCategory.FOOD -> Icons.Default.ShoppingCart
                                    ExpenseCategory.UTILITY -> Icons.Default.Settings
                                },
                                contentDescription = "Category: ${expense.category.displayName}",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        categoryColor,
                                        categoryColor.copy(alpha = 0.7f)
                                    )
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (expense.category) {
                                ExpenseCategory.STAFF -> Icons.Default.Person
                                ExpenseCategory.TRAVEL -> Icons.Default.LocationOn
                                ExpenseCategory.FOOD -> Icons.Default.ShoppingCart
                                ExpenseCategory.UTILITY -> Icons.Default.Settings
                            },
                            contentDescription = "Category: ${expense.category.displayName}",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = expense.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Text(
                        text = expense.category.displayName,
                        style = MaterialTheme.typography.bodySmall,
                        color = categoryColor,
                        fontWeight = FontWeight.Medium
                    )
                    
                    expense.notes?.let { notes ->
                        Text(
                            text = notes,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            
            // Right section with amount
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = expense.getFormattedAmount(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = "Today", // TODO: Format actual date
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Group header card for grouped view.
 */
@Composable
private fun GroupHeaderCard(
    title: String,
    count: Int,
    total: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            // AI-generated: Consistent surface variant background
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "₹${String.format("%.2f", total)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = "$count expenses",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Loading shimmer effect.
 */
@Composable
private fun LoadingShimmer(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_offset"
    )
    
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(6) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.1f),
                                    Color.White.copy(alpha = 0.3f),
                                    Color.White.copy(alpha = 0.1f)
                                ),
                                start = Offset(shimmerOffset - 300, 0f),
                                end = Offset(shimmerOffset, 0f)
                            )
                        )
                )
            }
        }
    }
}

/**
 * Stunning empty state with animation.
 */
@Composable
private fun StunningEmptyState(
    onAddExpense: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "empty_state_animation")
    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating_animation"
    )
    
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        
        // Floating animated icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(y = floatingOffset.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF667eea).copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = Color.White.copy(alpha = 0.8f)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No Expenses Yet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Start tracking your expenses by adding your first expense",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Gradient button
        Button(
            onClick = onAddExpense,
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF667eea),
                            Color(0xFF764ba2)
                        )
                    ),
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add First Expense",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseListScreenPreview() {
    SmartExpenseTrackerTheme {
        ExpenseListContent(
            uiState = ExpenseListUiState(
                filteredExpenses = listOf(
                    Expense.create(
                        title = "Office Lunch",
                        amount = 250.0,
                        category = ExpenseCategory.FOOD,
                        notes = "Team lunch at cafe"
                    ),
                    Expense.create(
                        title = "Taxi Fare",
                        amount = 120.0,
                        category = ExpenseCategory.TRAVEL
                    ),
                    Expense.create(
                        title = "Internet Bill",
                        amount = 1500.0,
                        category = ExpenseCategory.UTILITY
                    )
                ),
                totalAmount = 1870.0,
                totalExpenses = 3,
                todayTotal = 1870.0,
                todayExpenseCount = 3
            ),
            onAction = { },
            onNavigateToAddExpense = { },
            onNavigateToExpenseDetail = { }
        )
    }
}
