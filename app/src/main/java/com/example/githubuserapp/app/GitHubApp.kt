package com.example.githubuserapp.app

import android.app.Application
import com.example.githubuserapp.app.components.AppComponent
import com.example.githubuserapp.app.components.DaggerAppComponent
import dagger.android.DaggerApplication
import dagger.android.DaggerService

/**
 * Application class to initialize Dagger
 * */
class GitHubApp : Application(){

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }
}