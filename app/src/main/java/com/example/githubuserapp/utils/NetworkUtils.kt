package com.example.githubuserapp.utils

const val BASE_URL = "https://api.github.com/"

object Network {
    fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}