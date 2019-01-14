package com.pixelart.newyorktimesapi.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.pixelart.newyorktimesapi.common.API_KEY
import com.pixelart.newyorktimesapi.data.model.APIResponse
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.model.Response
import com.pixelart.newyorktimesapi.data.network.NetworkService
import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl
import com.pixelart.newyorktimesapi.factories.DataSourceFactory
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel/* @Inject constructor*/(private val repositoryImpl: RepositoryImpl): ViewModel() {

    lateinit var docPagedList: LiveData<PagedList<Doc>>
    lateinit var liveDataSource: LiveData<PageKeyedDataSource<Int, Doc>>

    init {
        val docDataSourceFactory = DataSourceFactory()
        liveDataSource = docDataSourceFactory.getDocLiveDataSource()

        val pagedListConfig:PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10).build()

        docPagedList = LivePagedListBuilder(docDataSourceFactory, pagedListConfig).build()
    }

    fun getArticles(query: String, filterQuery: String, beginDate: String,
                    endDate: String, sort: String, page: Int) : LiveData<Response> =
        repositoryImpl.getArticles(query, filterQuery, beginDate, endDate, sort, page, API_KEY)
    
}