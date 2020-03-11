package com.example.githubuserapp.utils

import android.content.Context
import android.net.ConnectivityManager

const val BASE_URL = "https://api.github.com/"

object Network {

    /**
     * Checking for internet connection without passing context (Not using Connectivity manager
     * which needs context to check network state). Here we will get internet state by pinging at
     * google.com. If Ping is successful return true else false
     * */
    fun internetIsConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeConnection = connectivityManager.activeNetworkInfo
        return activeConnection?.isConnected ?: false
    }

    const val NO_INTERNET_ERROR = "No Internet connection"
}