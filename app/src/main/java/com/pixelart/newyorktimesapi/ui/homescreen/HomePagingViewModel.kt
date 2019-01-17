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

    private val docPagedList: LiveData<PagedList<Doc>>
    private val liveDataSource: LiveData<PageKeyedDataSource<Int, Doc>>
    private val docDataSourceFactory = dataSourceFactory

    init {
        liveDataSource = docDataSourceFactory.getDocLiveDataSource()
        val pagedListConfig:PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10).build()

        docPagedList = LivePagedListBuilder(docDataSourceFactory, pagedListConfig).build()

        //Log.d(TAG, "Loading Status: ${docDataSourceFactory.isLoading}")
    }
    
    fun getArticles():LiveData<PagedList<Doc>> = docPagedList
    
    fun getState() = docDataSourceFactory.getStateObservable()

    fun setQuery(query:String){
        docDataSourceFactory.query(query)
    }
    fun setQueryFilter(filter: String){
        docDataSourceFactory.queryFilter(filter)
    }
}