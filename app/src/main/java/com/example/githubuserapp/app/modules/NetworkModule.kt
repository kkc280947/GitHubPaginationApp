package com.example.githubuserapp.app.modules

import com.example.githubuserapp.api.GitHubApiService
import com.example.githubuserapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Network Module for initializing Retrofit instance to use in app
 * with HttpLoggingInterceptor and RxJava2CallAdapterFactory
 * */

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return Retrofit
            .Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApi(retrofit: Retrofit): GitHubApiService {
        return retrofit.create(GitHubApiService::class.java)
    }
}