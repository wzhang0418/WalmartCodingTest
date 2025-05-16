package com.zhang.walmartassessment.domain.repository

import com.zhang.walmartassessment.data.util.Result
import com.zhang.walmartassessment.domain.dto.Country
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing country-related data.
 *
 * Provides methods to retrieve a list of countries from a remote API.
 */
interface ICountryRepository {

    /**
     * Fetches a list of countries from a remote API.
     *
     * @return A [Flow] that emits a [Result] containing either a list of [Country] objects
     *         on success or an exception on failure.
     */
    suspend fun getCountryListFromApi(): Flow<Result<List<Country>>>
}
