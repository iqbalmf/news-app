package net.iqbalfauzan.newsapplication.common.utils

import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.common.utils
 */
suspend fun <T : Any> ApiCall(
    call: suspend () -> Response<T>
): NetworkStatus<T> {
    try {
        val response = call.invoke()
        if (response.isSuccessful) {
            if (response.body() != null) {
                return NetworkStatus.Success(response.body())
            }
        }
        val errorRaw = response.errorBody().toString()
        val codeResp = response.code()
        return NetworkStatus.Error(
            if (!response.message().isNullOrBlank()) {
                response.message()
            }
            else if (codeResp == 404) {
                "404 Not Found"
            } else if (codeResp == 500) {
                "500 Internal Server Error"
            } else if (codeResp == 504) {
                "504 Response Timeout"
            } else if (codeResp == 400){
                "400 Param Missing"
            }
            else {
                errorRaw
            }
        )
    } catch (e: Exception) {
        return when (e) {
            is NoConnectionInterceptor.NoConnectivityException -> {
                NetworkStatus.Error(e.message)
            }
            is ConnectException -> {
                NetworkStatus.Error("CONNECT_EXCEPTION")
            }
            is UnknownHostException -> {
                NetworkStatus.Error("UNKNOWN_HOST_EXCEPTION")
            }
            is SocketTimeoutException -> {
                NetworkStatus.Error("SOCKET_TIME_OUT_EXCEPTION")
            }
            is HttpException -> {
                NetworkStatus.Error("UNKNOWN_NETWORK_EXCEPTION")
            }
            else -> {
                NetworkStatus.Error("UNKNOWN_NETWORK_EXCEPTION")
            }
        }
    }
}