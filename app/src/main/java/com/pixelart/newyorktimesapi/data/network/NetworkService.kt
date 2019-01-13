package com.pixelart.newyorktimesapi.data.network

import com.pixelart.newyorktimesapi.data.model.APIResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("search/v2/articlesearch.json")
    fun getArticles(
        @Query("q") query: String,
        @Query("fq")filterQuery: String,
        //@Query("begin_date") beginDate: String,
        //@Query("end_date") endDate: String,
        //@Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("api-key") apiKey: String
    ): Single<APIResponse>
}