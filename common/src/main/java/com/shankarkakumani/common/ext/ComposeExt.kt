// AI-generated: Compose extension functions for common UI operations
package com.shankarkakumani.common.ext

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Creates a vertical spacer with the specified height.
 */
@Composable
fun VerticalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

/**
 * Creates a horizontal spacer with the specified width.
 */
@Composable
fun HorizontalSpacer(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

/**
 * Common spacing values used throughout the app.
 */
object Spacing {
    val extraSmall = 4.dp
    val small = 8.dp
    val medium = 16.dp
    val large = 24.dp
    val extraLarge = 32.dp
}

/**
 * Creates vertical spacers with predefined sizes.
 */
@Composable
fun ExtraSmallVerticalSpacer() = VerticalSpacer(Spacing.extraSmall)

@Composable
fun SmallVerticalSpacer() = VerticalSpacer(Spacing.small)

@Composable
fun MediumVerticalSpacer() = VerticalSpacer(Spacing.medium)

@Composable
fun LargeVerticalSpacer() = VerticalSpacer(Spacing.large)

@Composable
fun ExtraLargeVerticalSpacer() = VerticalSpacer(Spacing.extraLarge)

/**
 * Creates horizontal spacers with predefined sizes.
 */
@Composable
fun ExtraSmallHorizontalSpacer() = HorizontalSpacer(Spacing.extraSmall)

@Composable
fun SmallHorizontalSpacer() = HorizontalSpacer(Spacing.small)

@Composable
fun MediumHorizontalSpacer() = HorizontalSpacer(Spacing.medium)

@Composable
fun LargeHorizontalSpacer() = HorizontalSpacer(Spacing.large)

@Composable
fun ExtraLargeHorizontalSpacer() = HorizontalSpacer(Spacing.extraLarge)
