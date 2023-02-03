package com.colaman.mvi

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import java.util.Properties
import kotlin.reflect.KProperty1
import kotlin.system.exitProcess

/**
 *
 * Author   : kyle
 *
 * Date     : 2023/1/22
 *
 * Function :
 *
 */

fun <T> MutableStateFlow<T>.copyAndDispatch(block: T.() -> T) {
    value = block.invoke(value)
}

fun <T> eventDispatcher(strategy: EventStrategy): MutableSharedFlow<T> {
    return MutableSharedFlow(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = if (strategy == EventStrategy.ALL) Int.MAX_VALUE else 1
    )
}

suspend fun <T, V> Flow<T>.distinctCollect(
    properties: KProperty1<T, V?>,
    collector: FlowCollector<V?>
) {
    map { properties.get(it) }
        .distinctUntilChanged()
        .collect(collector)
}