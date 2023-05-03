package org.sopar.domain.repository

import org.sopar.data.remote.response.SearchResponse
import retrofit2.Response

interface KakaoRepository {
    suspend fun getSearchLocation(token: String, query: String): Response<SearchResponse>
}