package com.ilkeruzer.cointicker.util.extention

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

fun CoroutineScope.launchPeriodicAsync(
        repeatMillis: Long,
        action: () -> Unit
) = this.async {
    if (repeatMillis > 0) {
        while (true) {
            action()
            delay(repeatMillis)
        }
    } else {
        action()
    }
}