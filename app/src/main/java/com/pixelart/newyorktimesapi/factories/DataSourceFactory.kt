package com.pixelart.newyorktimesapi.factories

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.network.NetworkService
import com.pixelart.newyorktimesapi.data.repository.ArticleDataSource

class DataSourceFactory(private val networkService: NetworkService): DataSource.Factory<Int, Doc>() {
    private val TAG = "DataSourceFactory"

    var isLoading = ObservableBoolean()
    private val docLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Doc>>()
    private lateinit var dataSource: ArticleDataSource

    override fun create(): DataSource<Int, Doc> {
        dataSource = ArticleDataSource(networkService, query, queryFilter, 0)
        isLoading = dataSource.getLoadingStatus()
        Log.d(TAG, "${dataSource.getLoadingStatus()}")
        docLiveDataSource.postValue(dataSource)
        return dataSource
    }

    fun getDocLiveDataSource():MutableLiveData<PageKeyedDataSource<Int, Doc>> = docLiveDataSource

    fun query(query: String){
       if(::dataSource.isInitialized){
           dataSource.setQuery(query)
       }
        this.query = query
    }

    fun queryFilter(queryFilter: String){
        if (::dataSource.isInitialized){
            dataSource.setFilter(queryFilter)
        }
        this.queryFilter = queryFilter
    }
    
    fun loadingState(): Boolean{
        return if (::dataSource.isInitialized)
            dataSource.getLoadingStatus().get()
        else false
    }

    private var query: String = ""
    private var queryFilter: String = ""
}