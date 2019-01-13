package com.pixelart.newyorktimesapi.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pixelart.newyorktimesapi.common.API_KEY
import com.pixelart.newyorktimesapi.data.model.APIResponse
import com.pixelart.newyorktimesapi.data.model.Response
import com.pixelart.newyorktimesapi.data.network.NetworkService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val networkService: NetworkService): ViewModel() {

    /*fun getArticles(query: String, filterQuery: String, beginDate: String,
                    endDate: String, sort: String, page: Int) : LiveData<Response> =
        repositoryImpl.getArticles(query, filterQuery, beginDate, endDate, sort, page, API_KEY)*/

    private val response = MutableLiveData<Response>()

    fun getArticles(
        query: String,
        filterQuery: String,
        beginDate: String,
        endDate: String,
        sort: String,
        page: Int
    ): LiveData<Response> {

        networkService.getArticles(query, filterQuery, /*beginDate, endDate, sort,*/ page, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<APIResponse> {
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