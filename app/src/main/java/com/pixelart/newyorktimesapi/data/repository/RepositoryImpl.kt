package com.pixelart.newyorktimesapi.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pixelart.newyorktimesapi.data.model.APIResponse
import com.pixelart.newyorktimesapi.data.model.Response
import com.pixelart.newyorktimesapi.data.network.NetworkService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RepositoryImpl (private val networkService: NetworkService): Repository{
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
}