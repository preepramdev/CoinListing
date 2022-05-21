package com.maxnimal.coin.listing.presentation.extension

import java.text.DecimalFormat

fun Double.formatCurrency(format: String): String {
    val dec = DecimalFormat(format)
    return dec.format(this)
}