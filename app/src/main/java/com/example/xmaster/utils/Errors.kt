package com.example.xmaster.utils

import com.example.xmaster.R

sealed class GeneralError(message: String) : Exception(message)

class LoadCoinsFailed(message: String = ""): GeneralError(message)
class LoadPicturesFailed(message: String = ""): GeneralError(message)
class NetworkUnavailable(message: String = ""): GeneralError(message)
class Unexpected(message: String = "") : GeneralError(message)


fun GeneralError.mapToStringResource() = when(this){
        is NetworkUnavailable -> R.string.lost_internet
        else -> R.string.unexpected
    }
