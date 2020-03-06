package com.example.githubuserapp.ui

import android.os.Bundle
import com.example.githubuserapp.R
import com.example.githubuserapp.base.BaseActivity
import com.example.githubuserapp.ui.userdetails.UserDetailFragment
import com.example.githubuserapp.ui.userslist.UserListFragment

class MainActivity : BaseActivity(), IActivityCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAppComponent().inject(this)
        if(savedInstanceState == null){
            swapFragment(UserListFragment.newInstance(),false)
        }
    }

    override fun moveToUserDetails(id: String) {
        swapFragment(UserDetailFragment.newInstance(id),true)
    }

    override fun getContainerViewId(): Int {
        return R.id.frame_container
    }
}
