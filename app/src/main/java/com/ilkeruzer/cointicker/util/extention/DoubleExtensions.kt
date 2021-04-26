package com.ilkeruzer.cointicker.util.extention

import java.text.NumberFormat
import java.util.Locale

fun Double?.orZero() = this ?: 0.0

fun Double?.toCurrency(locale: Locale = Locale("en", "US")): String = NumberFormat
        .getCurrencyInstance(locale)
        .format(this.orZero())
        .orEmpty()

