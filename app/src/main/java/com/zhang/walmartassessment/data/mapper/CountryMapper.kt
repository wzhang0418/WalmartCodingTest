package com.zhang.walmartassessment.data.mapper

import com.zhang.walmartassessment.data.model.CountryDetail
import com.zhang.walmartassessment.domain.dto.Country

/**
 * Utility class to map data transfer objects (DTOs) to domain models.
 * Specifically, it maps a [CountryDetail] to a [Country] domain model.
 */
object CountryMapper {

    /**
     * Maps a [CountryDetail] object to a [Country] domain model.
     *
     * @param countryDetail The [CountryDetail] object that contains the country information.
     * @return A [Country] domain model with mapped properties from [countryDetail].
     */
    private fun mapToDomain(countryDetail: CountryDetail): Country {
        return Country(
            capital = countryDetail.capital,
            code = countryDetail.code,
            name = countryDetail.name,
            region = countryDetail.region
        )
    }

    /**
     * Maps a list of [CountryDetail] objects to a list of [Country] domain models.
     *
     * @param countryDetails A list of [CountryDetail] objects to be mapped.
     * @return A list of [Country] domain models derived from the provided [countryDetails].
     */
    fun mapToDomainList(countryDetails: List<CountryDetail>): List<Country> {
        return countryDetails.map { mapToDomain(it) }
    }
}
