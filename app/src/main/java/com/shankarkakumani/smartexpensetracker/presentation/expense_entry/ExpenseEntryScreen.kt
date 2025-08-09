// AI-generated: Expense entry screen with Material 3 design
package com.shankarkakumani.smartexpensetracker.presentation.expense_entry

import android.widget.Toast
import android.content.Context
import android.net.Uri
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shankarkakumani.domain.model.ExpenseCategory
import com.shankarkakumani.smartexpensetracker.ui.theme.SmartExpenseTrackerTheme

/**
 * Main expense entry screen composable.
 * Follows Material 3 design principles with proper accessibility.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEntryScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpenseEntryViewModel = hiltViewModel(),
    onNavigateToExpenses: () -> Unit = {},
    onNavigateToReports: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    // Handle success/error messages with Toast
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.onAction(ExpenseEntryAction.ClearMessages)
            // Navigate to expense list after successful addition
            onNavigateToExpenses()
        }
    }
    
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.onAction(ExpenseEntryAction.ClearMessages)
        }
    }
    
    ExpenseEntryContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

/**
 * Content composable separated for testability.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpenseEntryContent(
    uiState: ExpenseEntryUiState,
    onAction: (ExpenseEntryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        
        // Header with today's total
        TodayTotalCard(
            uiState = uiState,
            onRefresh = { onAction(ExpenseEntryAction.RefreshTotalToday) }
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                
                Text(
                    text = "Add New Expense",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                // Title field
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { onAction(ExpenseEntryAction.UpdateTitle(it)) },
                    label = { Text("Expense Title") },
                    leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                    isError = uiState.titleError != null,
                    supportingText = uiState.titleError?.let { { Text(it) } },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = "Expense title input field" }
                )
                
                // Amount field
                OutlinedTextField(
                    value = uiState.amount,
                    onValueChange = { onAction(ExpenseEntryAction.UpdateAmount(it)) },
                    label = { Text("Amount (₹)") },
                    leadingIcon = { Icon(Icons.Default.Add, contentDescription = null) },
                    isError = uiState.amountError != null,
                    supportingText = uiState.amountError?.let { { Text(it) } },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = "Expense amount input field" }
                )
                
                // Category dropdown
                CategoryDropdownField(
                    selectedCategory = uiState.selectedCategory,
                    showDropdown = uiState.showCategoryDropdown,
                    onCategorySelected = { onAction(ExpenseEntryAction.UpdateCategory(it)) },
                    onToggleDropdown = { onAction(ExpenseEntryAction.ToggleCategoryDropdown) }
                )
                
                // Notes field
                OutlinedTextField(
                    value = uiState.notes,
                    onValueChange = { onAction(ExpenseEntryAction.UpdateNotes(it)) },
                    label = { Text("Notes (Optional)") },
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                    isError = uiState.notesError != null,
                    supportingText = uiState.notesError?.let { { Text(it) } } ?: {
                        Text("${uiState.notes.length}/100 characters")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = "Optional notes input field" }
                )

                // AI-generated: Receipt image attach + preview
                ReceiptImageSection(
                    imagePath = uiState.receiptImageUrl,
                    onPickImage = { onAction(ExpenseEntryAction.UpdateReceiptUrl(it)) },
                    onRemoveImage = { onAction(ExpenseEntryAction.UpdateReceiptUrl(null)) }
                )
                
                // Submit button
                Button(
                    onClick = { onAction(ExpenseEntryAction.SubmitExpense) },
                    enabled = uiState.isSubmitEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .semantics { contentDescription = "Submit expense button" }
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "Add Expense",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

// AI-generated: Helper composable to attach and preview receipt image
@Composable
private fun ReceiptImageSection(
    imagePath: String?,
    onPickImage: (String?) -> Unit,
    onRemoveImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val savedPath = copyUriToCache(context, uri)
            onPickImage(savedPath)
        }
    }

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(onClick = { launcher.launch("image/*") }) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Attach receipt (optional)")
            }
            if (imagePath != null) {
                TextButton(onClick = onRemoveImage) { Text("Remove") }
            }
        }

        if (imagePath != null) {
            val bitmap = remember(imagePath) {
                try {
                    BitmapFactory.decodeFile(imagePath)?.asImageBitmap()
                } catch (e: Exception) { null }
            }
            if (bitmap != null) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Receipt preview",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

// AI-generated: Copy the picked content URI into app cache and return absolute path
private fun copyUriToCache(context: Context, uri: Uri): String? {
    return try {
        val fileName = "receipt_${System.currentTimeMillis()}.jpg"
        val destFile = java.io.File(context.cacheDir, fileName)
        context.contentResolver.openInputStream(uri)?.use { input ->
            destFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        destFile.absolutePath
    } catch (_: Exception) {
        null
    }
}

/**
 * Today's total display card with animated updates.
 */
@Composable
private fun TodayTotalCard(
    uiState: ExpenseEntryUiState,
    onRefresh: () -> Unit
) {
    val cardColor by animateColorAsState(
        targetValue = if (uiState.totalSpentToday > 0) 
            MaterialTheme.colorScheme.primaryContainer 
        else 
            MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(300),
        label = "Card color animation"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = "Today's total spending summary" },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Today's Total",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            AnimatedVisibility(
                visible = true,
                enter = scaleIn(animationSpec = tween(300)) + fadeIn(),
                exit = scaleOut(animationSpec = tween(300)) + fadeOut()
            ) {
                Text(
                    text = uiState.getFormattedTotalToday(),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Text(
                text = uiState.getTodaySummary(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            TextButton(
                onClick = onRefresh,
                modifier = Modifier
                    .semantics { contentDescription = "Refresh total button" }
            ) {
                Text("Refresh")
            }
        }
    }
}

/**
 * Category dropdown field with proper Material 3 styling.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryDropdownField(
    selectedCategory: ExpenseCategory,
    showDropdown: Boolean,
    onCategorySelected: (ExpenseCategory) -> Unit,
    onToggleDropdown: () -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = showDropdown,
        onExpandedChange = { onToggleDropdown() }
    ) {
        OutlinedTextField(
            value = selectedCategory.displayName,
            onValueChange = { },
            readOnly = true,
            label = { Text("Category") },
            leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
            trailingIcon = { 
                Icon(
                    Icons.Default.ArrowDropDown, 
                    contentDescription = "Category dropdown arrow"
                ) 
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .semantics { contentDescription = "Category selection dropdown" }
        )
        
        ExposedDropdownMenu(
            expanded = showDropdown,
            onDismissRequest = onToggleDropdown
        ) {
            ExpenseCategory.getAllCategories().forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.displayName) },
                    onClick = {
                        onCategorySelected(category)
                    },
                    modifier = Modifier.semantics { 
                        contentDescription = "Select ${category.displayName} category" 
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseEntryScreenPreview() {
    SmartExpenseTrackerTheme {
        ExpenseEntryContent(
            uiState = ExpenseEntryUiState(
                title = "Office Lunch",
                amount = "250.00",
                selectedCategory = ExpenseCategory.FOOD,
                totalSpentToday = 1250.0,
                todayExpenseCount = 3
            ),
            onAction = { }
        )
    }
}
