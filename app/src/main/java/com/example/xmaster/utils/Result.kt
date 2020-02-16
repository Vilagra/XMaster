package com.example.xmaster.utils

import androidx.lifecycle.MutableLiveData

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: GeneralError) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data


inline fun <T> Result<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
    if (this is Result.Success) {
        liveData.value = data
    }
}

fun <T> Result<T>.isLoading(): Boolean {
    return this is Result.Loading
}

fun <T> Result<T>.handleError(block: (error: GeneralError) -> Unit) {
    if (this is Result.Error) block(exception)
}

fun <T> Result<T>.handleSuccess(block: (T) -> Unit) {
    if (this is Result.Success) block(this.data)
}

sealed class GeneralError(message: String) : Exception(message)

class LoadCoinsFailed(message: String = ""): GeneralError(message)

class LoadPicturesFailed(message: String = ""): GeneralError(message)

class Unexpected(message: String = "") : GeneralError(message)
