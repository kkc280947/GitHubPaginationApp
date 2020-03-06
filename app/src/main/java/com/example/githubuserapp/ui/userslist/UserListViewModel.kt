package com.example.githubuserapp.ui.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.githubuserapp.repository.models.GitHubProfileData
import com.example.githubuserapp.repository.datasource.GitHubDataSource
import com.example.githubuserapp.repository.datasource.GitHubDataSourceFactory
import com.example.githubuserapp.repository.NetworkState
import javax.inject.Inject

class UserListViewModel @Inject constructor(private val gitHubDataSourceFactory: GitHubDataSourceFactory) : ViewModel() {

    lateinit var userList: LiveData<PagedList<GitHubProfileData>>

    fun onScreenCreated() {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .build()
        userList = LivePagedListBuilder<Long, GitHubProfileData>(gitHubDataSourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        gitHubDataSourceFactory.dispose()
    }

    fun retry() {
        gitHubDataSourceFactory.gitHubDataSourceLiveData.value!!.retry()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<GitHubDataSource, NetworkState>(
        gitHubDataSourceFactory.gitHubDataSourceLiveData
    ) {
        it.networkState
    }

    fun getInitialLoadStatus(): LiveData<NetworkState> = Transformations.switchMap<GitHubDataSource, NetworkState>(
        gitHubDataSourceFactory.gitHubDataSourceLiveData
    ) {
        it.initialLoad
    }
}