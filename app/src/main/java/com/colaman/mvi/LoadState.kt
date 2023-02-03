package com.colaman.mvi

/**
 *
 * Author   : kyle
 *
 * Date     : 2023/1/21
 *
 * Function :
 *
 */
sealed class LoadState {
    object Loading : LoadState()
    object Success : LoadState()
    data class Error(val message: String) : LoadState()
}
