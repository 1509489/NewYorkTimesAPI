package com.pixelart.newyorktimesapi.factories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.network.NetworkService
import com.pixelart.newyorktimesapi.data.repository.ArticleDataSource

class DataSourceFactory(private val networkService: NetworkService): DataSource.Factory<Int, Doc>() {

    private var query: String = ""
    private var queryFilter: String = ""

    private val docLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Doc>>()

    override fun create(): DataSource<Int, Doc> {
        val dataSource = ArticleDataSource(networkService, query, queryFilter, 0)
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
}