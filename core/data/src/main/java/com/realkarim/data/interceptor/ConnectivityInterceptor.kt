package com.realkarim.data.interceptor

import com.realkarim.data.connectivity.NetworkMonitor
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val networkMonitor: NetworkMonitor) :
  Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    if (networkMonitor.hasConnectivity()) {
      return chain.proceed(chain.request())
    } else {
      throw NoConnectivityException
    }
  }
}

object NoConnectivityException : IOException()
