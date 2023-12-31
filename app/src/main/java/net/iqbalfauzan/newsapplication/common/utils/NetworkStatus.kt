package net.iqbalfauzan.newsapplication.common.utils

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.utils
 */
sealed class NetworkStatus<T>(val data: T? = null, val errorMessage: String? = null) {
    class Success<T>(data: T?) : NetworkStatus<T>(data)
    class Error<T>(errorMessage: String?, data: T? = null) : NetworkStatus<T>(data, errorMessage)
    class Loading<T>(data: T? = null) : NetworkStatus<T>(data)
}