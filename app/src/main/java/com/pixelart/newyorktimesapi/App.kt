package com.pixelart.newyorktimesapi

import android.app.Application
import com.pixelart.newyorktimesapi.di.application.ApplicationComponent
import com.pixelart.newyorktimesapi.di.application.ApplicationModule
//import com.pixelart.newyorktimesapi.di.application.DaggerApplicationComponent

class App: Application() {
    /*val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }*/
}