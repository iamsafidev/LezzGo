package com.confiz.lezzgo.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * debounce helps to detect the state when no new data is submitted for some time, effectively
 * allowing you to process a data when the input is completed.
 */
fun <T> debounce(waitMs: Long = 300L, scope: CoroutineScope, listener: (T) -> Unit): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(waitMs)
            listener(param)
        }
    }
}