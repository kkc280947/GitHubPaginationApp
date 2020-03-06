package com.example.githubuserapp.app.modules

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class RxModule {

    @Provides
    @Singleton
    fun providesCompositeDisposable() = CompositeDisposable()
}