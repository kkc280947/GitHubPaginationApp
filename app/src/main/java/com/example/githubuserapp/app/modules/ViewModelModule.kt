package com.example.githubuserapp.app.modules

import androidx.lifecycle.ViewModel
import com.example.githubuserapp.ui.userdetails.UserDetailViewModel
import com.example.githubuserapp.ui.userslist.UserListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

/**
* ViewModel module to hold all the viewmodel in map.
* */

@Module
abstract class ViewModelModule{

    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun bindUserListViewModel(mUserListViewModel: UserListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailViewModel::class)
    abstract fun bindUserDetailViewModel(mUserDetailViewModel: UserDetailViewModel): ViewModel
}