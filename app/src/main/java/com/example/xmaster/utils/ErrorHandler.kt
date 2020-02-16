package com.example.xmaster.utils

import android.content.Context
import com.example.xmaster.R
import javax.inject.Inject


open class ErrorHandler @Inject constructor(
        val context: Context
) {
    fun convertError(cause: GeneralError) =
            when (cause) {
                is Unexpected -> context.getString(R.string.unexpected)
                else -> null
            }
}

fun String?.ifNullSetDefault(str: String) = this ?: str