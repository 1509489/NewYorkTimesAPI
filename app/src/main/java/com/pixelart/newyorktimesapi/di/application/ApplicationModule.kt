package com.pixelart.newyorktimesapi.di.application

import android.app.Application
import android.content.Context
import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationScope
    fun providesContext(): Context = application

/*    @Provides
    fun providesRepository(repositoryImpl: RepositoryImpl) : RepositoryImpl = repositoryImpl*/
}