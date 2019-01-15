package com.pixelart.newyorktimesapi.di.application

import android.app.Application
import android.content.Context
import com.pixelart.newyorktimesapi.data.network.NetworkService
import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl
import com.pixelart.newyorktimesapi.di.network.NetworkModule
import com.pixelart.newyorktimesapi.factories.DataSourceFactory
import com.pixelart.newyorktimesapi.factories.HomePagingViewModelFactory
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationScope
    //@Singleton
    fun providesContext(): Context = application

    @Provides
    //@Singleton
    @ApplicationScope
    fun providesRepository(networkService: NetworkService) : RepositoryImpl = RepositoryImpl(networkService)

    @Provides
    @ApplicationScope
    fun providesDataSourceFactory(networkService: NetworkService): DataSourceFactory = DataSourceFactory(networkService)

    @Provides
    @ApplicationScope
    fun providesHomePagingViewModelFactory(dataSourceFactory: DataSourceFactory): HomePagingViewModelFactory = HomePagingViewModelFactory(dataSourceFactory)
}