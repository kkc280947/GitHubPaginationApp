package com.example.githubuserapp.repository.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.example.githubuserapp.repository.models.GitHubProfileData
import com.example.githubuserapp.repository.NetworkState
import com.example.githubuserapp.api.GitHubApiService
import com.example.githubuserapp.utils.Network.NO_INTERNET_ERROR
import com.example.githubuserapp.utils.Network.internetIsConnected
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * DataSource class to provide paginated list to viewmodel then to UI.
 * */
class GitHubDataSource @Inject constructor(private val gitHubApiService: GitHubApiService, val compositeDisposable: CompositeDisposable) : ItemKeyedDataSource<Long, GitHubProfileData>(){

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    /**
     * Keep Completable reference for the retry event
     */
    private var retryCompletable: Completable? = null

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ }, { throwable ->Log.d("error",throwable.message ?: "") }))
        }
    }

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<GitHubProfileData>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        compositeDisposable.add(gitHubApiService.getUsers(0, params.requestedLoadSize).subscribe({ users ->
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(users)
        }, { throwable ->
            setRetry(Action { loadInitial(params, callback) })
            val error =
                NetworkState.error(
                    throwable.message
                )
            networkState.postValue(error)
            initialLoad.postValue(error)
        }))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<GitHubProfileData>) {
        networkState.postValue(NetworkState.LOADING)
        //get the users from the api after id
        compositeDisposable.add(gitHubApiService.getUsers(params.key, params.requestedLoadSize).subscribe({ users ->
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(users)
        }, { throwable ->
            setRetry(Action {
                loadAfter(params, callback) })
            networkState.postValue(
                NetworkState.error(
                    throwable.message
                )
            )
        }))
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<GitHubProfileData>) {
    }

    override fun getKey(item: GitHubProfileData): Long {
        return item.id
    }

    fun dispose(){
        compositeDisposable.dispose()
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }
}
