package com.example.githubuserapp.repository.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.githubuserapp.repository.models.GitHubProfileData
import javax.inject.Inject
import javax.inject.Singleton


/**
 * DataSourceFactory class to get build datasource, according to request by user scrolling.
 * */
@Singleton
class GitHubDataSourceFactory @Inject constructor(val mGitHubDataSource: GitHubDataSource) :
    DataSource.Factory<Long, GitHubProfileData>() {

    val gitHubDataSourceLiveData: MutableLiveData<GitHubDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, GitHubProfileData> {
        gitHubDataSourceLiveData.postValue(mGitHubDataSource)
        return mGitHubDataSource
    }

    fun dispose(){
        mGitHubDataSource.dispose()
    }
}