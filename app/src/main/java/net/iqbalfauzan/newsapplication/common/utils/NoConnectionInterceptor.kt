package net.iqbalfauzan.newsapplication.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import net.iqbalfauzan.newsapplication.common.NO_CONNECTION_INTERNET
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by IqbalMF on 2023.
 * Package net.iqbalfauzan.newsapplication.common.utils
 */
class NoConnectionInterceptor constructor(
    private val mContext: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnectionOn(mContext)) {
            throw NoConnectivityException()
        } else {
            chain.proceed(chain.request())
        }
    }

    private fun isConnectionOn(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    class NoConnectivityException : IOException() {
        override val message: String
            get() = NO_CONNECTION_INTERNET
    }
}