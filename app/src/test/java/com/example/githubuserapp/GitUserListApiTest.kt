package com.example.githubuserapp

import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import java.net.HttpURLConnection


/**
 * Test to check weather we are correct response while
 * getting 20 users on first fetch and asserting if list is not empty.
 * */
class GitUserListApiTest :BaseNetworkMockTest(){

    @Test
    fun testFirstResponse() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getListOfUsersJson)
        getMockServer().enqueue(response)
        val users = getApiService().getUsers(0).blockingGet()
        assert(!users.isNullOrEmpty())
    }
}