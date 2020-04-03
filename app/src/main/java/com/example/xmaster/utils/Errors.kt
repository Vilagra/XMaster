package com.example.xmaster.utils

sealed class GeneralError(message: String) : Exception(message)

class LoadCoinsFailed(message: String = ""): GeneralError(message)
class LoadPicturesFailed(message: String = ""): GeneralError(message)
class Unexpected(message: String = "") : GeneralError(message)
