package com.colaman.mvi

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

/**
 *
 * Author   : kyle
 *
 * Date     : 2023/1/21
 *
 * Function :
 *
 */
class StoreCollector<T>(
    private val store: Store<T>,
    private val scope: CoroutineScope
) {

    fun onStateChange(collector: FlowCollector<T>) {
        scope.launch {
            store.getStateDispatcher().collect(collector)
        }
    }

    fun onEventChange(collector: FlowCollector<Any>) {
        scope.launch {
            store.getEventDispatcher().collect(collector)
        }
    }

    fun <V> onDistinct(properties: KProperty1<T, V?>, collector: FlowCollector<V?>) {
        scope.launch {
            store.getStateDispatcher().distinctCollect(properties, collector)
        }
    }

    companion object {
        suspend fun <T> collect(
            store: Store<T>,
            scope: CoroutineScope,
            callback: suspend StoreCollector<T>.() -> Unit
        ) {
            StoreCollector(store, scope).also {
                callback.invoke(it)
            }
        }
    }

    fun <T> collect(
        store: Store<T>,
        lifecycleOwner: LifecycleOwner,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        callback: StoreCollector<T>.() -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(state) {
                collect(store, this, callback)
            }
        }
    }
}
