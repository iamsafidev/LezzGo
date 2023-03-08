package com.confiz.lezzgo.data.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

class NetworkHandler @Inject constructor(
    private val connectivityManager: ConnectivityManager?
) {

    val isConnected: Boolean
        get() = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.activeNetwork?.let { network ->
                connectivityManager.getNetworkCapabilities(network)?.run {
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)     -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else                                                 -> false
                    }
                }
            } ?: false
        } else {
            connectivityManager?.activeNetworkInfo?.run {
                when(type) {
                    ConnectivityManager.TYPE_WIFI     -> true
                    ConnectivityManager.TYPE_MOBILE   -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else                              -> false
                }
            } ?: false
        }

}