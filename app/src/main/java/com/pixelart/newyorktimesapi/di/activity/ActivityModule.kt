package com.pixelart.newyorktimesapi.di.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.pixelart.newyorktimesapi.R
import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl
import com.pixelart.newyorktimesapi.databinding.ActivityArticleListBinding
import com.pixelart.newyorktimesapi.factories.HomeViewModelFactory
import com.pixelart.newyorktimesapi.ui.homescreen.HomeViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    //@Singleton
    @ActivityScope
    fun providesHomescreenViewModelFactory(repositoryImpl: RepositoryImpl) = HomeViewModelFactory(repositoryImpl)

    @Provides
    @ActivityScope
    //@Singleton
    fun providesHomeScreenViewModel(homeViewModelFactory: HomeViewModelFactory) =
        ViewModelProviders.of(activity, homeViewModelFactory).get(HomeViewModel::class.java)

    @Provides
    @ActivityScope
    fun providesHomescreenBinding() = DataBindingUtil.setContentView<ActivityArticleListBinding>(activity, R.layout.activity_article_list)
}