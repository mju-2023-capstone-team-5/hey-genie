package org.sopar.data.api

import org.sopar.data.remote.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoRetrofitApi {

    @GET("v2/local/search/keyword.json")
    suspend fun getSearchLocation(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): Response<SearchResponse>

}