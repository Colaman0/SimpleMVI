package com.colaman.mvi

import com.colaman.mvi.test.MainState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *
 * Author   : kyle
 *
 * Date     : 2023/1/22
 *
 * Function :
 *
 */
fun main() = runBlocking {
    val store = MVIStore(MainState())

    StoreCollector.collect(store, this) {
//        onStateChange {
//            println("onStateChange: $it")
//        }
//        onDistinct(MainState::name) {
//            println("onDistinct: $it")
//        }

        onEventChange {
            println("onEventChange: $it")
        }
    }
    delay(1000)
    store.dispatchEvent(MainState(name = "kyle", age = 10))
    store.dispatchEvent(MainState(name = "kyele", age = 10))
    store.dispatchEvent(MainState(name = "kyele", age = 12))
}