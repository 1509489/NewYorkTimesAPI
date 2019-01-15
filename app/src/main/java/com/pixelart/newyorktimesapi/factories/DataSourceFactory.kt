package com.pixelart.newyorktimesapi.factories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.network.NetworkService
import com.pixelart.newyorktimesapi.data.repository.ArticleDataSource

class DataSourceFactory(private val networkService: NetworkService): DataSource.Factory<Int, Doc>() {
    private val TAG = "DataSourceFactory"

    var isLoading: Boolean = false
    private val docLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Doc>>()

    override fun create(): DataSource<Int, Doc> {
        val dataSource = ArticleDataSource(networkService, getQuery(), queryFilter, 0)
        isLoading = dataSource.getLoadingStatus()
        Log.d(TAG, "${dataSource.getLoadingStatus()}")
        docLiveDataSource.postValue(dataSource)
        return dataSource
    }

    fun getDocLiveDataSource():MutableLiveData<PageKeyedDataSource<Int, Doc>> = docLiveDataSource

    fun query(query: String){
        this.query = query
    }

    fun queryFilter(queryFilter: String){
        this.queryFilter = queryFilter
    }

    private var query: String = ""
    private var queryFilter: String = ""
    private fun getQuery():String = query
}