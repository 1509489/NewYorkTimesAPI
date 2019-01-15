package com.pixelart.newyorktimesapi.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pixelart.newyorktimesapi.common.API_KEY
import com.pixelart.newyorktimesapi.data.model.Response
import com.pixelart.newyorktimesapi.data.repository.RepositoryImpl

class HomeViewModel/* @Inject constructor*/(private val repositoryImpl: RepositoryImpl): ViewModel() {


    fun getArticles(query: String, filterQuery: String, beginDate: String,
                    endDate: String, sort: String, page: Int) : LiveData<Response> =
        repositoryImpl.getArticles(query, filterQuery, beginDate, endDate, sort, page, API_KEY)

}