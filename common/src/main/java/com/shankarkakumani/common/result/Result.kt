// AI-generated: Result wrapper for consistent error handling across all modules
package com.shankarkakumani.common.result

/**
 * A generic wrapper for operations that can succeed or fail.
 * This follows the Result pattern for consistent error handling across the app.
 */
sealed class Result<out T> {
    
    /**
     * Represents a successful operation with the result data.
     */
    data class Success<out T>(val data: T) : Result<T>()
    
    /**
     * Represents a failed operation with an error.
     */
    data class Error(val exception: Throwable) : Result<Nothing>()
    
    /**
     * Represents a loading state for operations in progress.
     */
    object Loading : Result<Nothing>()
}

/**
 * Returns the data if this is a Success, or null otherwise.
 */
fun <T> Result<T>.getDataOrNull(): T? {
    return when (this) {
        is Result.Success -> data
        else -> null
    }
}

/**
 * Returns true if this is a Success.
 */
fun <T> Result<T>.isSuccess(): Boolean = this is Result.Success

/**
 * Returns true if this is an Error.
 */
fun <T> Result<T>.isError(): Boolean = this is Result.Error

/**
 * Returns true if this is Loading.
 */
fun <T> Result<T>.isLoading(): Boolean = this is Result.Loading

/**
 * Executes the given block if this is a Success.
 */
inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) {
        action(data)
    }
    return this
}

/**
 * Executes the given block if this is an Error.
 */
inline fun <T> Result<T>.onError(action: (Throwable) -> Unit): Result<T> {
    if (this is Result.Error) {
        action(exception)
    }
    return this
}

/**
 * Executes the given block if this is Loading.
 */
inline fun <T> Result<T>.onLoading(action: () -> Unit): Result<T> {
    if (this is Result.Loading) {
        action()
    }
    return this
}

/**
 * Maps the data of a successful result to a new type.
 */
inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> Result.Error(exception)
        is Result.Loading -> Result.Loading
    }
}
