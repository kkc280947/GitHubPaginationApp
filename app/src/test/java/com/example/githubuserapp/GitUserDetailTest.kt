package com.example.githubuserapp

import com.example.githubuserapp.repository.NetworkState
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

    @Test
    fun FailDetailResponse() {
        val users = getApiService().getUsersDetails("kkc280947").doOnError { NetworkState.error("403 Gateway").toString() }
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_GATEWAY)
            .setBody(users.toString())
        getMockServer().enqueue(response)
        if(response.status == "HTTP/1.1 502 Server Error"){
            assert(true)
        } else {
            assert(false)
        }
    }
}