// AI-generated: Theme toggle component for Smart Expense Tracker
package com.shankarkakumani.smartexpensetracker.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shankarkakumani.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme
import com.shankarkakumani.smartexpensetracker.ui.theme.ThemeMode

/**
 * Animated theme toggle switch
 */
@Composable
fun ThemeToggleSwitch(
    currentTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    Box(modifier = modifier) {
        // Main toggle button
        Surface(
            modifier = Modifier
                .size(48.dp)
                .clickable { expanded = !expanded },
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            tonalElevation = 6.dp
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = currentTheme,
                    transitionSpec = {
                        slideInVertically { -it } + fadeIn() togetherWith
                                slideOutVertically { it } + fadeOut()
                    },
                    label = "themeIcon"
                ) { theme ->
                    Icon(
                        imageVector = when (theme) {
                            ThemeMode.LIGHT -> Icons.Default.WbSunny
                            ThemeMode.DARK -> Icons.Default.NightsStay
                            ThemeMode.SYSTEM -> Icons.Default.Settings
                        },
                        contentDescription = "Theme toggle",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        
        // Expanded options
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            ) + fadeOut()
        ) {
            Card(
                modifier = Modifier
                    .offset(y = (-8).dp)
                    .width(200.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    ThemeOption(
                        icon = Icons.Default.WbSunny,
                        text = "Light",
                        selected = currentTheme == ThemeMode.LIGHT,
                        onClick = {
                            onThemeChange(ThemeMode.LIGHT)
                            expanded = false
                        }
                    )
                    
                    ThemeOption(
                        icon = Icons.Default.NightsStay,
                        text = "Dark",
                        selected = currentTheme == ThemeMode.DARK,
                        onClick = {
                            onThemeChange(ThemeMode.DARK)
                            expanded = false
                        }
                    )
                    
                    ThemeOption(
                        icon = Icons.Default.Settings,
                        text = "System",
                        selected = currentTheme == ThemeMode.SYSTEM,
                        onClick = {
                            onThemeChange(ThemeMode.SYSTEM)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

/**
 * Individual theme option item
 */
@Composable
private fun ThemeOption(
    icon: ImageVector,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            Color.Transparent
        },
        animationSpec = tween(200),
        label = "themeOptionBackground"
    )
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(20.dp),
            tint = if (selected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        AnimatedVisibility(
            visible = selected,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            )
        }
    }
}

/**
 * Simple theme toggle button
 */
@Composable
fun SimpleThemeToggle(
    isDarkTheme: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (isDarkTheme) 180f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "themeRotation"
    )
    
    IconButton(
        onClick = onToggle,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isDarkTheme) Icons.Default.NightsStay else Icons.Default.WbSunny,
            contentDescription = if (isDarkTheme) "Switch to light theme" else "Switch to dark theme",
            modifier = Modifier
                .size(24.dp)
                .graphicsLayer(rotationY = rotation)
        )
    }
}

/**
 * Compact theme mode indicator
 */
@Composable
fun ThemeModeIndicator(
    themeMode: ThemeMode,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = when (themeMode) {
                ThemeMode.LIGHT -> Icons.Default.WbSunny
                ThemeMode.DARK -> Icons.Default.NightsStay
                ThemeMode.SYSTEM -> Icons.Default.Settings
            },
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = when (themeMode) {
                ThemeMode.LIGHT -> "Light"
                ThemeMode.DARK -> "Dark"
                ThemeMode.SYSTEM -> "System"
            },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ThemeTogglePreview() {
    SmartExpenseTrackerTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ThemeToggleSwitch(
                currentTheme = ThemeMode.LIGHT,
                onThemeChange = {}
            )
            
            SimpleThemeToggle(
                isDarkTheme = false,
                onToggle = {}
            )
            
            ThemeModeIndicator(themeMode = ThemeMode.SYSTEM)
        }
    }
}
