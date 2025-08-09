// AI-generated: Base ViewModel class for consistent error handling and coroutine management
package com.shankarkakumani.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shankarkakumani.common.result.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel class that provides common functionality for all ViewModels.
 * Includes error handling, loading states, and coroutine management.
 */
abstract class BaseViewModel : ViewModel() {
    
    // Loading state management
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Error state management
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    /**
     * Global exception handler for all coroutines in ViewModels.
     */
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleError(exception)
    }
    
    /**
     * Executes a suspend function with proper error handling and loading states.
     * 
     * @param showLoading Whether to show loading state during execution
     * @param onError Custom error handling function
     * @param block The suspend function to execute
     */
    protected fun launchWithErrorHandling(
        showLoading: Boolean = true,
        onError: ((Throwable) -> Unit)? = null,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            try {
                if (showLoading) setLoading(true)
                clearError()
                block()
            } catch (exception: Throwable) {
                onError?.invoke(exception) ?: handleError(exception)
            } finally {
                if (showLoading) setLoading(false)
            }
        }
    }
    
    /**
     * Executes a suspend function that returns a Result and handles it appropriately.
     */
    protected fun <T> launchWithResult(
        showLoading: Boolean = true,
        onSuccess: (T) -> Unit,
        onError: ((Throwable) -> Unit)? = null,
        block: suspend () -> Result<T>
    ) {
        launchWithErrorHandling(showLoading, onError) {
            when (val result = block()) {
                is Result.Success -> onSuccess(result.data)
                is Result.Error -> {
                    onError?.invoke(result.exception) ?: handleError(result.exception)
                }
                is Result.Loading -> {
                    // Loading is handled by launchWithErrorHandling
                }
            }
        }
    }
    
    /**
     * Sets the loading state.
     */
    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
    
    /**
     * Handles errors by setting the error state.
     */
    protected fun handleError(throwable: Throwable) {
        _error.value = throwable.message ?: "An unknown error occurred"
    }
    
    /**
     * Clears the current error state.
     */
    protected fun clearError() {
        _error.value = null
    }
    
    /**
     * Shows a custom error message.
     */
    protected fun showError(message: String) {
        _error.value = message
    }
}
