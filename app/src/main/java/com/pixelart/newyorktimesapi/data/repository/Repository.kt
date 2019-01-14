package com.pixelart.newyorktimesapi.data.repository

import androidx.lifecycle.LiveData
import com.pixelart.newyorktimesapi.data.model.APIResponse
import com.pixelart.newyorktimesapi.data.model.Response
import io.reactivex.Single

interface Repository {
    fun getArticles(query: String, filterQuery: String, beginDate: String,
                    endDate: String, sort: String, page: Int, apiKey: String): LiveData<Response>

}