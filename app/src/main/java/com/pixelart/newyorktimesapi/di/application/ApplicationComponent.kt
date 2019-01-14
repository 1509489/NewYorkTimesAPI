package com.pixelart.newyorktimesapi.di.application

import com.pixelart.newyorktimesapi.di.activity.*
import dagger.Component

@ApplicationScope
//@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

   fun newActivityComponent(activityModule: ActivityModule): ActivityComponent

}