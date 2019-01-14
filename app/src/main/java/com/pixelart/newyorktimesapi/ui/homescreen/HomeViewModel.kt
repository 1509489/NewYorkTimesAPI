package com.pixelart.newyorktimesapi.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pixelart.newyorktimesapi.common.API_KEY
import com.pixelart.newyorktimesapi.data.model.Response
import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl

class HomeViewModel/* @Inject constructor*/(private val repositoryImpl: RepositoryImpl): ViewModel() {

    /*lateinit var docPagedList: LiveData<PagedList<Doc>>
    lateinit var liveDataSource: LiveData<PageKeyedDataSource<Int, Doc>>
    lateinit var networkService: NetworkService*/

    /*init {
        val docDataSourceFactory = DataSourceFactory(networkService)
        liveDataSource = docDataSourceFactory.getDocLiveDataSource()

        val pagedListConfig:PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10).build()

        docPagedList = LivePagedListBuilder(docDataSourceFactory, pagedListConfig).build()
    }*/

    fun getArticles(query: String, filterQuery: String, beginDate: String,
                    endDate: String, sort: String, page: Int) : LiveData<Response> =
        repositoryImpl.getArticles(query, filterQuery, beginDate, endDate, sort, page, API_KEY)

}