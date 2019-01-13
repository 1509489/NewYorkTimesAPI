package com.pixelart.newyorktimesapi.di.activity

import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl
import com.pixelart.newyorktimesapi.ui.homescreen.ArticleListActivity
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

//@ActivityScope
@Singleton
@Component(modules = [ActivityModule::class])
interface ActivityComponent {

    fun injectHomeScreen(articleListActivity: ArticleListActivity)
    //fun getRepository(): RepositoryImpl
}