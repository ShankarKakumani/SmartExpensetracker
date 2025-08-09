// AI-generated: Spacing tokens and CompositionLocal for consistent spacing across the app
package com.shankarkakumani.smartexpensetracker.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

/**
 * Spacing scale for consistent paddings and margins
 */
data class Spacing(
    val xs: androidx.compose.ui.unit.Dp = 4.dp,
    val sm: androidx.compose.ui.unit.Dp = 8.dp,
    val md: androidx.compose.ui.unit.Dp = 12.dp,
    val lg: androidx.compose.ui.unit.Dp = 16.dp,
    val xl: androidx.compose.ui.unit.Dp = 24.dp,
    val xxl: androidx.compose.ui.unit.Dp = 32.dp
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

/**
 * Convenient accessor: MaterialTheme.spacing
 */
val androidx.compose.material3.MaterialTheme.spacing: Spacing
    @Composable
    get() = LocalSpacing.current


