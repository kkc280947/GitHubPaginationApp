package com.example.githubuserapp.api

import com.example.githubuserapp.repository.models.GitHubProfileData
import com.example.githubuserapp.repository.models.GitHubUserDetailData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * API interface for REST
 * */
interface GitHubApiService {

    @GET("users")
    fun getUsers(@Query("since") itemNumber: Long, @Query("per_page") itemPerPage: Int = 20): Single<MutableList<GitHubProfileData>>

    @GET("users/{username}")
    fun getUsersDetails(@Path("username") userId: String): Single<GitHubUserDetailData>
}
