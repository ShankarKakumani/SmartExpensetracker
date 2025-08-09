// AI-generated: Animated components and micro-interactions for Smart Expense Tracker
package com.shankarkakumani.smartexpensetracker.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shankarkakumani.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme
import kotlinx.coroutines.delay

/**
 * Enhanced clickable modifier with scale animation and haptic feedback
 */
fun Modifier.animatedClickable(
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    indication: androidx.compose.foundation.Indication? = null,
    hapticEnabled: Boolean = true,
    scaleDown: Float = 0.95f,
    onClick: () -> Unit
): Modifier = composed {
    val haptic = LocalHapticFeedback.current
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) scaleDown else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    this
        .scale(scale)
        .pointerInput(enabled) {
            detectTapGestures(
                onPress = {
                    if (enabled) {
                        isPressed = true
                        if (hapticEnabled) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                        tryAwaitRelease()
                        isPressed = false
                    }
                },
                onTap = {
                    if (enabled) onClick()
                }
            )
        }
}

/**
 * Floating action button with enhanced animations
 */
@Composable
fun AnimatedFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    icon: ImageVector = Icons.Default.Add,
    contentDescription: String? = null,
    visible: Boolean = true
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ) + fadeOut()
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = modifier.animatedClickable(onClick = onClick),
            containerColor = containerColor,
            contentColor = contentColor
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
    }
}

/**
 * Animated card that slides in from bottom
 */
@Composable
fun SlideInCard(
    visible: Boolean,
    modifier: Modifier = Modifier,
    delayMillis: Int = 0,
    content: @Composable () -> Unit
) {
    LaunchedEffect(visible) {
        if (delayMillis > 0) {
            delay(delayMillis.toLong())
        }
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it / 2 },
            animationSpec = tween(600, delayMillis, easing = FastOutSlowInEasing)
        ) + fadeIn(
            animationSpec = tween(600, delayMillis)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it / 2 },
            animationSpec = tween(400, easing = FastOutLinearInEasing)
        ) + fadeOut(
            animationSpec = tween(400)
        )
    ) {
        Box(modifier = modifier) {
            content()
        }
    }
}

/**
 * Bouncing success indicator
 */
@Composable
fun SuccessIndicator(
    visible: Boolean,
    modifier: Modifier = Modifier,
    onAnimationEnd: () -> Unit = {}
) {
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        finishedListener = { if (!visible) onAnimationEnd() },
        label = "successScale"
    )
    
    val rotation by animateFloatAsState(
        targetValue = if (visible) 0f else -90f,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "successRotation"
    )
    
    if (visible || scale > 0f) {
        Box(
            modifier = modifier
                .size(80.dp)
                .scale(scale)
                .graphicsLayer(rotationZ = rotation)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Success",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

/**
 * Animated counter with rolling number effect
 */
@Composable
fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.headlineMedium
) {
    var oldCount by remember { mutableStateOf(count) }
    var animationState by remember { mutableStateOf(AnimationState.Stable) }
    
    val animatedCount by animateIntAsState(
        targetValue = count,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        finishedListener = {
            animationState = AnimationState.Stable
        },
        label = "counter"
    )
    
    LaunchedEffect(count) {
        if (count != oldCount) {
            animationState = AnimationState.Animating
            oldCount = count
        }
    }
    
    val scale by animateFloatAsState(
        targetValue = when (animationState) {
            AnimationState.Animating -> 1.1f
            AnimationState.Stable -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "counterScale"
    )
    
    Text(
        text = animatedCount.toString(),
        style = style,
        modifier = modifier.scale(scale)
    )
}

private enum class AnimationState {
    Stable, Animating
}

/**
 * Pulse animation modifier
 */
fun Modifier.pulseAnimation(
    enabled: Boolean = true,
    minScale: Float = 0.9f,
    maxScale: Float = 1.1f,
    duration: Int = 1000
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = minScale,
        targetValue = maxScale,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    
    if (enabled) scale(scale) else this
}

/**
 * Category selection item with animation
 */
@Composable
fun AnimatedCategoryItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) color else Color.Transparent,
        animationSpec = tween(300),
        label = "categoryBackground"
    )
    
    val textColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(300),
        label = "categoryText"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (selected) color else MaterialTheme.colorScheme.outline,
        animationSpec = tween(300),
        label = "categoryBorder"
    )
    
    Surface(
        modifier = modifier
            .animatedClickable(onClick = onClick)
            .clip(RoundedCornerShape(20.dp)),
        color = backgroundColor,
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

/**
 * Loading dots animation
 */
@Composable
fun LoadingDots(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    dotCount: Int = 3
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loadingDots")
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(dotCount) { index ->
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, delayMillis = index * 200),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "dotAlpha$index"
            )
            
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = color.copy(alpha = alpha),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimatedComponentsPreview() {
    SmartExpenseTrackerTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnimatedCounter(count = 42)
            LoadingDots()
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AnimatedCategoryItem(
                    text = "Food",
                    selected = true,
                    onClick = {}
                )
                AnimatedCategoryItem(
                    text = "Travel",
                    selected = false,
                    onClick = {}
                )
            }
        }
    }
}
