// AI-generated: Stunning expense report screen with charts, analytics and beautiful gradients
package com.shankarkakumani.smartexpensetracker.presentation.expense_report

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme
import java.time.LocalDate
import kotlin.math.cos
import kotlin.math.sin

/**
 * Main expense report screen with stunning analytics and charts.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseReportScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpenseReportViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    // Handle messages
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.onAction(ExpenseReportAction.ClearMessages)
        }
    }
    
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.onAction(ExpenseReportAction.ClearMessages)
        }
    }
    
    ExpenseReportContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateBack = onNavigateBack,
        modifier = modifier
    )
}

/**
 * Main content with stunning gradients and analytics.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpenseReportContent(
    uiState: ExpenseReportUiState,
    onAction: (ExpenseReportAction) -> Unit,
    onNavigateBack: () -> Unit,
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
            
            // AI-generated: Removed Analytics app bar and share/export control per request
            
            // Content
            when {
                uiState.isLoading -> {
                    AnalyticsLoadingState(modifier = Modifier.fillMaxSize())
                }
                uiState.shouldShowEmptyState() -> {
                    AnalyticsEmptyState(modifier = Modifier.fillMaxSize())
                }
                uiState.hasData() -> {
                    AnalyticsMainContent(
                        uiState = uiState,
                        onAction = onAction,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        
        // AI-generated: Removed export dialog trigger
    }
}

/**
 * Beautiful app bar with glassmorphism effect.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnalyticsAppBar(
    onNavigateBack: () -> Unit,
    onAction: (ExpenseReportAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.3f),
                        Color.White.copy(alpha = 0.1f)
                    )
                )
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            
            // Back button
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.3f),
                                Color.White.copy(alpha = 0.1f)
                            )
                        ),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            
            // Title
            Text(
                text = "Analytics",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            // Export button
            IconButton(
                onClick = { onAction(ExpenseReportAction.ToggleExportDialog) },
                modifier = Modifier
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFFD700).copy(alpha = 0.8f),
                                Color(0xFFFFA500).copy(alpha = 0.6f)
                            )
                        ),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.Share,
                    contentDescription = "Export",
                    tint = Color.White
                )
            }
        }
    }
}

/**
 * Main analytics content (date selector removed as requested).
 */
@Composable
private fun AnalyticsMainContent(
    uiState: ExpenseReportUiState,
    onAction: (ExpenseReportAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        
        // AI-generated: Period/date selector removed
        
        // Summary cards
        item {
            SummaryCardsRow(uiState = uiState)
        }
        
        // Weekly chart
        if (uiState.weeklyChartData.isNotEmpty()) {
            item {
                WeeklyChartCard(
                    chartData = uiState.weeklyChartData,
                    onChartEntrySelected = { onAction(ExpenseReportAction.SelectChartEntry(it)) }
                )
            }
        }
        
        // Category breakdown
        if (uiState.categoryBreakdown.isNotEmpty()) {
            item {
                CategoryBreakdownCard(
                    categories = uiState.categoryBreakdown,
                    onCategorySelected = { /* Handle category selection */ }
                )
            }
        }
        
        // Daily totals
        if (uiState.dailyTotals.isNotEmpty()) {
            item {
                DailyTotalsCard(dailyTotals = uiState.dailyTotals)
            }
        }
        
        // Insights
        if (uiState.insights.isNotEmpty()) {
            item {
                InsightsCard(
                    insights = uiState.insights,
                    onDismissInsight = { onAction(ExpenseReportAction.DismissInsight(it)) }
                )
            }
        }
        
        // Recommendations
        if (uiState.recommendations.isNotEmpty()) {
            item {
                RecommendationsCard(recommendations = uiState.recommendations)
            }
        }
    }
}

/**
 * Period selector card with gradient styling.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PeriodSelectorCard(
    selectedPeriod: String,
    onPeriodClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onPeriodClick,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color(0xFF667eea).copy(alpha = 0.3f),
                spotColor = Color(0xFF764ba2).copy(alpha = 0.5f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Period",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF718096)
                )
                Text(
                    text = selectedPeriod,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3748)
                )
            }
            
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                tint = Color(0xFF667eea),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

/**
 * Summary cards row with key metrics.
 */
@Composable
private fun SummaryCardsRow(
    uiState: ExpenseReportUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        
        // Total spent card
        SummaryCard(
            title = "Total Spent",
            value = uiState.getFormattedTotal(),
            icon = Icons.Default.Star,
            color = Color(0xFF667eea),
            modifier = Modifier.weight(1f)
        )
        
        // Daily average card
        SummaryCard(
            title = "Daily Avg",
            value = uiState.getFormattedDailyAverage(),
            icon = Icons.Default.Favorite,
            color = Color(0xFF764ba2),
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Individual summary card with gradient and animation.
 */
@Composable
private fun SummaryCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    val animatedValue by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "summary_card_animation"
    )
    
    Card(
        modifier = modifier
            .scale(animatedValue)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = color.copy(alpha = 0.3f),
                spotColor = color.copy(alpha = 0.5f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            // Icon with gradient background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                color,
                                color.copy(alpha = 0.7f)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF718096)
            )
            
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748)
            )
        }
    }
}

/**
 * Weekly chart card with beautiful styling.
 */
@Composable
private fun WeeklyChartCard(
    chartData: List<ChartEntry>,
    onChartEntrySelected: (ChartEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color(0xFF667eea).copy(alpha = 0.3f),
                spotColor = Color(0xFF764ba2).copy(alpha = 0.5f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            
            Text(
                text = "Weekly Spending",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Simple bar chart representation
            SimpleBarChart(
                data = chartData,
                onBarSelected = onChartEntrySelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

/**
 * Simple bar chart implementation.
 */
@Composable
private fun SimpleBarChart(
    data: List<ChartEntry>,
    onBarSelected: (ChartEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return
    
    val maxValue = data.maxOfOrNull { it.value } ?: 1f
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { entry ->
            val animatedHeight by animateFloatAsState(
                targetValue = (entry.value / maxValue),
                animationSpec = tween(1000, easing = FastOutSlowInEasing),
                label = "bar_height_animation"
            )
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                
                // Amount text
                Text(
                    text = "₹${entry.value.toInt()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF718096),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Bar
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .fillMaxHeight(animatedHeight.coerceAtLeast(0.05f))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF667eea),
                                    Color(0xFF764ba2)
                                )
                            ),
                            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        )
                        .clickable { onBarSelected(entry) }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Day label
                Text(
                    text = entry.label,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF718096),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Category breakdown card with pie chart representation.
 */
@Composable
private fun CategoryBreakdownCard(
    categories: List<CategoryTotal>,
    onCategorySelected: (CategoryTotal) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color(0xFF667eea).copy(alpha = 0.3f),
                spotColor = Color(0xFF764ba2).copy(alpha = 0.5f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            
            Text(
                text = "Category Breakdown",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            categories.forEach { category ->
                CategoryBreakdownItem(
                    category = category,
                    onClick = { onCategorySelected(category) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (category != categories.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

/**
 * Individual category breakdown item.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryBreakdownItem(
    category: CategoryTotal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColor = when (category.category) {
        ExpenseCategory.STAFF -> Color(0xFF667eea)
        ExpenseCategory.TRAVEL -> Color(0xFF764ba2)
        ExpenseCategory.FOOD -> Color(0xFFf093fb)
        ExpenseCategory.UTILITY -> Color(0xFF4facfe)
    }
    
    val categoryIcon = when (category.category) {
        ExpenseCategory.STAFF -> Icons.Default.Person
        ExpenseCategory.TRAVEL -> Icons.Default.LocationOn
        ExpenseCategory.FOOD -> Icons.Default.ShoppingCart
        ExpenseCategory.UTILITY -> Icons.Default.Settings
    }
    
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = categoryColor.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            
            // Category icon
            Box(
                modifier = Modifier
                    .size(40.dp)
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
                    categoryIcon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Category details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.category.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2D3748)
                )
                
                Text(
                    text = "${category.expenseCount} expenses",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF718096)
                )
            }
            
            // Amount and percentage
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = category.getFormattedAmount(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D3748)
                )
                
                Text(
                    text = category.getFormattedPercentage(),
                    style = MaterialTheme.typography.bodySmall,
                    color = categoryColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Daily totals card with mini bars.
 */
@Composable
private fun DailyTotalsCard(
    dailyTotals: List<DayTotal>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color(0xFF667eea).copy(alpha = 0.3f),
                spotColor = Color(0xFF764ba2).copy(alpha = 0.5f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            
            Text(
                text = "Daily Breakdown",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            dailyTotals.forEach { dayTotal ->
                DailyTotalItem(
                    dayTotal = dayTotal,
                    maxAmount = dailyTotals.maxOfOrNull { it.amount } ?: 1.0,
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (dayTotal != dailyTotals.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

/**
 * Individual daily total item with mini progress bar.
 */
@Composable
private fun DailyTotalItem(
    dayTotal: DayTotal,
    maxAmount: Double,
    modifier: Modifier = Modifier
) {
    val progress = if (maxAmount > 0) (dayTotal.amount / maxAmount).toFloat() else 0f
    
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "daily_progress_animation"
    )
    
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        
        // Day name
        Text(
            text = dayTotal.getDateDisplayText(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF2D3748),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(48.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Progress bar
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .background(
                    color = Color(0xFFF7FAFC),
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF667eea),
                                Color(0xFF764ba2)
                            )
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Amount
        Text(
            text = dayTotal.getFormattedAmount(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF2D3748),
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Insights card with spending insights.
 */
@Composable
private fun InsightsCard(
    insights: List<SpendingInsight>,
    onDismissInsight: (SpendingInsight) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color(0xFF667eea).copy(alpha = 0.3f),
                spotColor = Color(0xFF764ba2).copy(alpha = 0.5f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            
            Text(
                text = "💡 Insights",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            insights.forEach { insight ->
                InsightItem(
                    insight = insight,
                    onDismiss = { onDismissInsight(insight) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (insight != insights.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

/**
 * Individual insight item.
 */
@Composable
private fun InsightItem(
    insight: SpendingInsight,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7FAFC)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = insight.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2D3748)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = insight.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF718096)
                )
            }
            
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Dismiss",
                    tint = Color(0xFF718096),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

/**
 * Recommendations card.
 */
@Composable
private fun RecommendationsCard(
    recommendations: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color(0xFF667eea).copy(alpha = 0.3f),
                spotColor = Color(0xFF764ba2).copy(alpha = 0.5f)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            
            Text(
                text = "🎯 Recommendations",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            recommendations.forEachIndexed { index, recommendation ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "${index + 1}.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF667eea),
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = recommendation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2D3748),
                        modifier = Modifier.weight(1f)
                    )
                }
                
                if (index < recommendations.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

/**
 * Export dialog for choosing export format.
 */
@Composable
private fun ExportDialog(
    onExportSelected: (ExportType) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Export Report",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text("Choose export format:")
                Spacer(modifier = Modifier.height(16.dp))
                
                ExportType.values().forEach { exportType ->
                    TextButton(
                        onClick = { onExportSelected(exportType) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(exportType.displayName)
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

/**
 * Loading state with beautiful shimmer effect.
 */
@Composable
private fun AnalyticsLoadingState(
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(4) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
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
 * Empty state for analytics.
 */
@Composable
private fun AnalyticsEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        
        Icon(
            Icons.Default.Info,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = Color.White.copy(alpha = 0.6f)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No Data Available",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Add some expenses to see analytics and insights",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseReportScreenPreview() {
    SmartExpenseTrackerTheme {
        ExpenseReportContent(
            uiState = ExpenseReportUiState(
                totalSpent = 5780.0,
                dailyAverage = 826.0,
                dailyTotals = listOf(
                    DayTotal(LocalDate.now().minusDays(6), 450.0, 3),
                    DayTotal(LocalDate.now().minusDays(5), 320.0, 2),
                    DayTotal(LocalDate.now().minusDays(4), 680.0, 4),
                    DayTotal(LocalDate.now().minusDays(3), 520.0, 3),
                    DayTotal(LocalDate.now().minusDays(2), 780.0, 5),
                    DayTotal(LocalDate.now().minusDays(1), 920.0, 6),
                    DayTotal(LocalDate.now(), 1108.0, 7)
                ),
                categoryBreakdown = listOf(
                    CategoryTotal(ExpenseCategory.FOOD, 2300.0, 12, 39.8),
                    CategoryTotal(ExpenseCategory.TRAVEL, 1500.0, 8, 26.0),
                    CategoryTotal(ExpenseCategory.UTILITY, 1200.0, 5, 20.8),
                    CategoryTotal(ExpenseCategory.STAFF, 780.0, 3, 13.4)
                ),
                insights = listOf(
                    SpendingInsight(
                        title = "High Food Spending",
                        description = "Food expenses are 40% of your total spending",
                        type = InsightType.CATEGORY_ALERT
                    )
                ),
                recommendations = listOf(
                    "Consider meal planning to reduce food costs",
                    "Track daily expenses for better awareness"
                )
            ),
            onAction = { },
            onNavigateBack = { }
        )
    }
}
