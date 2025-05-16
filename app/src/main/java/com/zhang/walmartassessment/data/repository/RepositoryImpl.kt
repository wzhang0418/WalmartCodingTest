package com.zhang.walmartassessment.data.repository

import com.zhang.walmartassessment.data.mapper.CountryMapper
import com.zhang.walmartassessment.data.api.ApiService
import com.zhang.walmartassessment.data.api.result
import com.zhang.walmartassessment.domain.repository.ICountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

/**
 * CountryRepository class to get country data from Api.
 * Fetch the country data and use mapper to map the list to our data model.
 */
class RepositoryImpl(
    private val apiService: ApiService
) : ICountryRepository {
    override suspend fun getCountryListFromApi() = result(
        call = { apiService.getCountryList() },
        mapper = { CountryMapper.mapToDomainList(it) }
    ).flowOn(Dispatchers.IO)
}