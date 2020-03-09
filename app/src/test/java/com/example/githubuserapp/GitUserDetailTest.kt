package com.example.githubuserapp

import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import java.net.HttpURLConnection


/**
 * Test cases to test weather, we are getting correct response while fetching git user information
 * */
class GitUserDetailTest :BaseNetworkMockTest(){

    @Test
    fun testUserDetail(){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getUserDetialMockResponse)
        getMockServer().enqueue(response)
        val users = getApiService().getUsersDetails("kkc280947").blockingGet()
        assert(users!=null)
    }
}