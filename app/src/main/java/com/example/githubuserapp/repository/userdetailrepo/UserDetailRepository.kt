package com.example.githubuserapp.repository.userdetailrepo

import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp.repository.models.GitHubUserDetailData
import com.example.githubuserapp.repository.NetworkState
import com.example.githubuserapp.api.GitHubApiService
import com.example.githubuserapp.repository.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Repository to get User detail result
 * */
class UserDetailRepository @Inject constructor(val gitHubApiService: GitHubApiService) {

    val networkState = MutableLiveData<NetworkState>()

    fun getUserDetails(userId: String): MutableLiveData<GitHubUserDetailData> {
        val userDetail = MutableLiveData<GitHubUserDetailData>()
        networkState.postValue(NetworkState.LOADING)
        gitHubApiService.getUsersDetails(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<GitHubUserDetailData>() {
                override fun onSuccess(t: GitHubUserDetailData) {
                    networkState.postValue(NetworkState.LOADED)
                    userDetail.postValue(t)
                }

                override fun onError(e: Throwable) {
                    networkState.postValue(NetworkState.error(e.message))
                }
            })
        return userDetail
    }
}