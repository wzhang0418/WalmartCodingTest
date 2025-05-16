package com.zhang.walmartassessment.data.api

import com.zhang.walmartassessment.data.model.CountryDetail
import com.zhang.walmartassessment.data.util.AppConstants.END_POINT
import retrofit2.Response
import retrofit2.http.GET

/**
 * interface to wrap call request to methods
 */
interface ApiService {
    @GET(END_POINT)
    suspend fun getCountryList(): Response<List<CountryDetail>>

    companion object {
        private lateinit var INSTANCE: ApiService
        fun getInstance(): ApiService {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = ApiClient.retrofit.create(ApiService::class.java)
            }
            return INSTANCE
        }
    }
}