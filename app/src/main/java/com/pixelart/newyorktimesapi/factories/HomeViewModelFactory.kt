package com.pixelart.newyorktimesapi.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pixelart.newyorktimesapi.data.network.NetworkService
import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl
import com.pixelart.newyorktimesapi.ui.homescreen.HomeViewModel
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(private val networkService: NetworkService): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)) HomeViewModel(networkService) as T
        else throw IllegalArgumentException("ViewModel not found")
    }
}