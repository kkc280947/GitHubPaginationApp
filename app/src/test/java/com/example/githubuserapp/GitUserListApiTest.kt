package com.example.githubuserapp

import com.example.githubuserapp.repository.NetworkState
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

    @Test
    fun FailFirstResponse() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_GATEWAY)
            .setBody(NetworkState.error("403 Gateway").toString())
        getMockServer().enqueue(response)
        if(response.status.contains(HttpURLConnection.HTTP_BAD_GATEWAY.toString())){
            assert(true)
        }else{
            assert(false)
        }
    }

    @Test
    fun testPaginationResponse() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getListOfUsersJson)
        getMockServer().enqueue(response)
        val users = getApiService().getUsers(18088110,20).blockingGet()
        assert(!users.isNullOrEmpty())
    }

}