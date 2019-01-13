package com.pixelart.newyorktimesapi.di.application

import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl
import com.pixelart.newyorktimesapi.di.activity.*
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    //fun newActivityComponent(activityModule: ActivityModule): ActivityComponent

}