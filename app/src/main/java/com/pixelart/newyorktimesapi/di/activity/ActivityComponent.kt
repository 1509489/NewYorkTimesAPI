package com.pixelart.newyorktimesapi.di.activity

import com.pixelart.newyorktimesapi.ui.homescreen.ArticleListActivity
import dagger.Subcomponent

@ActivityScope
//@Singleton
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun injectHomeScreen(articleListActivity: ArticleListActivity)
}
