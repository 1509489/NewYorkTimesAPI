package com.pixelart.newyorktimesapi.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.pixelart.newyorktimesapi.data.model.APIResponse
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.model.Response
import com.pixelart.newyorktimesapi.data.network.NetworkService
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers



class RepositoryImpl (private val networkService: NetworkService): PageKeyedDataSource<Int, Doc>(), Repository{
    private val FIRST_PAGE = 0

    var query: String? = null
    var filterQuery: String? = null
    var beginDate: String? = null
    var endDate: String? = null
    var sort: String? = null
    var page: Int? = null
    var apiKey: String? = null

    private val response = MutableLiveData<Response>()



    override fun getArticles(
        query: String,
        filterQuery: String,
        beginDate: String,
        endDate: String,
        sort: String,
        page: Int,
        apiKey: String
    ): LiveData<Response> {

        networkService.getArticles(query, filterQuery, /*beginDate, endDate, sort,*/ page, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<APIResponse>{
                override fun onSuccess(t: APIResponse) {
                    response.value = t.response
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }
            })
        return response
    }

    override fun getArticlesRespnse(
        query: String,
        filterQuery: String,
        beginDate: String,
        endDate: String,
        sort: String,
        page: Int,
        apiKey: String
    ): Single<APIResponse> {
        return networkService.getArticles(query, filterQuery, page, apiKey)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Doc>) {
        networkService.getArticles(query!!, filterQuery!!, /*beginDate, endDate, sort,*/ page!!, apiKey!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<APIResponse>{
                override fun onSuccess(t: APIResponse) {
                    if (t != null){
                        callback.onResult(t.response.docs, null, FIRST_PAGE + 1)
                    }
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Doc>) {
        networkService.getArticles(query!!, filterQuery!!, /*beginDate, endDate, sort,*/ page!!, apiKey!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<APIResponse>{
                override fun onSuccess(t: APIResponse) {

                    val adjacentKey = (if (params.key > 0) params.key - 1 else null)?.toInt()
                    if (t != null){
                        callback.onResult(t.response.docs, adjacentKey)
                    }
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Doc>) {
        networkService.getArticles(query!!, filterQuery!!, /*beginDate, endDate, sort,*/ page!!, apiKey!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<APIResponse>{
                override fun onSuccess(t: APIResponse) {
                    val pages:Int = (t.response.meta.hits / 10)

                    if (t != null){
                        val key = (if (params.key < pages) params.key + 1 else null)?.toInt()
                        callback.onResult(t.response.docs, key)
                    }
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

}