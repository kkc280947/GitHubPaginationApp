package com.example.githubuserapp

import com.example.githubuserapp.api.GitHubApiService
import com.example.githubuserapp.utils.BASE_URL
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

open class BaseNetworkMockTest {

    private var mockWebServer = MockWebServer()

    private lateinit var apiService: GitHubApiService

    @Before
    fun setup() {
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GitHubApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    fun getMockServer() = mockWebServer

    fun getApiService() = apiService
}