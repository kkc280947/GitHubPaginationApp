package com.example.githubuserapp.app.modules

import com.example.githubuserapp.repository.userdetailrepo.UserDetailRepository
import com.example.githubuserapp.api.GitHubApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module to initialize repositories that needs to be used in viewmodels
 * */
@Module
class RepoModule {

    @Provides
    @Singleton
    fun getUserDetailsRepo(gitHubApiService: GitHubApiService): UserDetailRepository {
        return UserDetailRepository(
            gitHubApiService
        )
    }
}