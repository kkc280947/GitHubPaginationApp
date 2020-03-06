package com.example.githubuserapp.app.components

import com.example.githubuserapp.ui.MainActivity
import com.example.githubuserapp.ui.userslist.UserListFragment
import com.example.githubuserapp.app.modules.NetworkModule
import com.example.githubuserapp.app.modules.RepoModule
import com.example.githubuserapp.app.modules.RxModule
import com.example.githubuserapp.app.modules.ViewModelModule
import com.example.githubuserapp.ui.userdetails.UserDetailFragment
import dagger.Component
import javax.inject.Singleton


/**
* AppComponent module to inject dependencies created, which will be used
*  in fragment and activities.
* */
@Singleton
@Component(
    modules = [ViewModelModule::class, NetworkModule::class, RxModule::class, RepoModule::class]
)
interface AppComponent{
    fun inject(mainActivity: MainActivity)
    fun inject(userListFragment: UserListFragment)
    fun inject(userDetailFragment: UserDetailFragment)
}