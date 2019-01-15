package com.pixelart.newyorktimesapi.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.factories.DataSourceFactory

class HomePagingViewModel (dataSourceFactory: DataSourceFactory):ViewModel() {
    //private val TAG = "HomePagingViewModel"

    var docPagedList: LiveData<PagedList<Doc>>
    private var liveDataSource: LiveData<PageKeyedDataSource<Int, Doc>>
/*
    private var query: String? = null
    private var filteredQuery: String? = null*/
    private val docDataSourceFactory = dataSourceFactory

    init {

        liveDataSource = docDataSourceFactory.getDocLiveDataSource()

        val pagedListConfig:PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10).build()

        docPagedList = LivePagedListBuilder(docDataSourceFactory, pagedListConfig).build()

        //Log.d(TAG, "Loading Status: ${docDataSourceFactory.isLoading}")
    }

    fun setQuery(query:String){
        docDataSourceFactory.query(query)
    }
    fun setQueryFilter(filter: String){
        docDataSourceFactory.queryFilter(filter)
    }

    fun isLoading(): Boolean =  docDataSourceFactory.isLoading
}