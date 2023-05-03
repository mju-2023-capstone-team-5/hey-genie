package org.sopar.data.repository

import org.sopar.data.api.KakaoRetrofitApi
import org.sopar.data.remote.response.SearchResponse
import org.sopar.domain.repository.KakaoRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoRepositoryImpl @Inject constructor(
    private val api: KakaoRetrofitApi
): KakaoRepository {
    override suspend fun getSearchLocation(token: String, query: String): Response<SearchResponse> {
        return api.getSearchLocation(token, query)
    }
}