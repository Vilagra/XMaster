package com.example.xmaster.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object NumberConverter{
    fun convertDigitOnTouthandsComaSeparator(num: Number) = NumberFormat.getNumberInstance(Locale.FRANCE).format(num)
    fun doubleWithTwoPointAfterComaToString (double: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(double)
    }
    fun doubleWithTwoPointAfterComa (double: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(double).toDouble()
    }
}