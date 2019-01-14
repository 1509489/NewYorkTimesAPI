package com.pixelart.newyorktimesapi.factories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.network.NetworkService
import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl

class DataSourceFactory(private val networkService: NetworkService): DataSource.Factory<Int, Doc>() {

    private val docLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Doc>>()

    override fun create(): DataSource<Int, Doc> {
        val repositoryImpl = RepositoryImpl(networkService)
        docLiveDataSource.postValue(repositoryImpl)
        return repositoryImpl
    }

    fun getDocLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, Doc>> = docLiveDataSource
}