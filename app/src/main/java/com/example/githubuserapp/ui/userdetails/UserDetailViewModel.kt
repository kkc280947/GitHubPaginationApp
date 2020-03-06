package com.example.githubuserapp.ui.userdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.repository.models.GitHubUserDetailData
import com.example.githubuserapp.repository.userdetailrepo.UserDetailRepository
import javax.inject.Inject

/**
 * Viewmodel class to save userdetails data.
 * */
class UserDetailViewModel @Inject constructor(val mUserDetailRepository: UserDetailRepository) : ViewModel(){

    fun getUserDetail(userId: String) : MutableLiveData<GitHubUserDetailData> {
        return mUserDetailRepository.getUserDetails(userId)
    }
}
