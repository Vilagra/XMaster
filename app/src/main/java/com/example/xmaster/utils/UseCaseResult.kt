package com.example.xmaster.utils


sealed class UseCaseResult<out R> {

    data class Success<out T>(val data: T) : UseCaseResult<T>()
    data class Error(val exception: GeneralError) : UseCaseResult<Nothing>()
    object Loading : UseCaseResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

fun <T> UseCaseResult<T>.successOr(fallback: T): T {
    return (this as? UseCaseResult.Success<T>)?.data ?: fallback
}

val <T> UseCaseResult<T>.data: T?
    get() = (this as? UseCaseResult.Success)?.data


fun <T> UseCaseResult<T>.handleResult(
    handleSuccess: (T) -> Unit = {},
    handleError: (error: GeneralError) -> Unit = {},
    handleLoading: (isLoading: Boolean) -> Unit = {}
){
    handleLoading(this is UseCaseResult.Loading)
    when(this){
        is UseCaseResult.Success -> handleSuccess(data)
        is UseCaseResult.Error -> handleError(exception)
    }
}

