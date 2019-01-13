package com.pixelart.newyorktimesapi.di.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl
import com.pixelart.newyorktimesapi.di.network.NetworkModule
import com.pixelart.newyorktimesapi.factories.HomeViewModelFactory
import com.pixelart.newyorktimesapi.ui.homescreen.HomeViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    //@ActivityScope
    @Singleton
    fun providesHomeScreenViewModel(homeViewModelFactory: HomeViewModelFactory) =
        ViewModelProviders.of(activity, homeViewModelFactory).get(HomeViewModel::class.java)
/*
    @Provides
    fun providesRepository(repositoryImpl: RepositoryImpl) : RepositoryImpl = repositoryImpl*/
}