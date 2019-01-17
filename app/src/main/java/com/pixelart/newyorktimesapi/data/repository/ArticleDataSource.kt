package com.pixelart.newyorktimesapi.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.pixelart.newyorktimesapi.common.API_KEY
import com.pixelart.newyorktimesapi.data.model.APIResponse
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.network.NetworkService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticleDataSource(
    private val networkService: NetworkService,
    private val query: String,
    private val filteredQuery: String,
    private var page: Int,
    private val stateObservable: MutableLiveData<State>
):
    PageKeyedDataSource<Int, Doc>(){
    //private val TAG = "ArticleDataSource"
    
    
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Doc>) {
        networkService.getArticles(query, filteredQuery, page, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<APIResponse>{
                override fun onSuccess(t: APIResponse) {
                    //Log.d(TAG, "Current Page: $page")
                    stateObservable.value = State.SUCCESS//(State.SUCCESS)
                    callback.onResult(t.response.docs, null, page+1)
                }

                override fun onSubscribe(d: Disposable) {
                    stateObservable.postValue(State.INITIALLOADING)
                }

                override fun onError(e: Throwable) {
                    stateObservable.value = State.FAILURE
                    e.printStackTrace()
                }
            })
    }
    
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Doc>) {
        networkService.getArticles(query, filteredQuery, page, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<APIResponse>{
                override fun onSuccess(t: APIResponse) {
                    stateObservable.value = State.SUCCESS
                    
                    val pages: Int = t.response.meta.hits / 10
                    val hasMore: Boolean
                    hasMore = pages > params.key
                    val nextPage = (if (hasMore) params.key +1 else null)?.toInt()
                    page = params.key

                    //Log.d(TAG, "Next Page: $page")
                    callback.onResult(t.response.docs, nextPage)
                    //Log.d(TAG, "Loading Status: $isLoading")
                }

                override fun onSubscribe(d: Disposable) {
                    stateObservable.postValue(State.LOADING)
                    //Log.d(TAG, "Loading Status: $isLoading")
                }

                override fun onError(e: Throwable) {
                    stateObservable.value = State.FAILURE
                    e.printStackTrace()
                }
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Doc>) {
        networkService.getArticles(query, filteredQuery, page, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<APIResponse>{
                override fun onSuccess(t: APIResponse) {
                    stateObservable.value = State.SUCCESS
                    val adjacentKey = (if (params.key > 0 ) params.key - 1 else null)?.toInt()
                    //Log.d(TAG, "Previous Page: ${params.key}")
                    callback.onResult(t.response.docs, adjacentKey)
                }

                override fun onSubscribe(d: Disposable) {
                    stateObservable.postValue(State.LOADING)
                }

                override fun onError(e: Throwable) {
                    stateObservable.value = State.FAILURE
                    e.printStackTrace()
                }
            })
    }
    
    fun setQuery(){
        invalidate()
    }
    
    fun setFilter(){
        invalidate()
    }
    
    enum class State{
        LOADING,
        INITIALLOADING,
        SUCCESS,
        FAILURE
    }
}