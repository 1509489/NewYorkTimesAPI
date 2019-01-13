package com.pixelart.newyorktimesapi.data.repository

import androidx.lifecycle.LiveData
import com.pixelart.newyorktimesapi.data.model.Response

interface Repository {
    fun getArticles(query: String, filterQuery: String, beginDate: String,
                    endDate: String, sort: String, page: Int, apiKey: String): LiveData<Response>
}