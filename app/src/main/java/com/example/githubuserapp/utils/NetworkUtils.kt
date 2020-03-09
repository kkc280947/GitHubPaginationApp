package com.example.githubuserapp.utils

const val BASE_URL = "https://api.github.com/"

object Network {

    /**
     * Checking for internet connection without passing context (Not using Connectivity manager
     * which needs context to check network state). Here we will get internet state by pinging at
     * google.com. If Ping is successful return true else false
     * */
    fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}