package com.pixelart.newyorktimesapi.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pixelart.newyorktimesapi.ui.homescreen.HomePagingViewModel

class HomePagingViewModelFactory(private val dataSourceFactory: DataSourceFactory): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomePagingViewModel::class.java)) HomePagingViewModel(dataSourceFactory) as T
        else throw IllegalArgumentException("ViewModel not found")
    }
}