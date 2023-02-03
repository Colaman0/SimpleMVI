package com.colaman.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 *
 * Author   : kyle
 *
 * Date     : 2023/1/18
 *
 * Function :
 *
 */
class MVIStore<T>(
    initValue: T
) : Store<T> {

    private val stateDispatcher = MutableStateFlow(initValue)
    private val eventDispatcher = eventDispatcher<Any>(EventStrategy.LATEST)

    override fun getStateDispatcher(): Flow<T> {
        return stateDispatcher
    }

    override fun getEventDispatcher(): Flow<Any> {
        return eventDispatcher
    }

    override fun state(): T {
        return stateDispatcher.value
    }

    fun dispatchState(block: T.() -> T) {
        stateDispatcher.copyAndDispatch(block)
    }

    fun dispatchState(state: T) {
        stateDispatcher.value = state
    }

    fun dispatchEvent(event: Any) {
        println(eventDispatcher.tryEmit(event))
    }
}
