package com.colaman.mvi

import kotlinx.coroutines.flow.Flow

/**
 *
 * Author   : kyle
 *
 * Date     : 2023/1/21
 *
 * Function :
 *
 */
interface Store<T> {

    fun getStateDispatcher(): Flow<T>

    fun getEventDispatcher(): Flow<Any>

    fun state(): T
}
