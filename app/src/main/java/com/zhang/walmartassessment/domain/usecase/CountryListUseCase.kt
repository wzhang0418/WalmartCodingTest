package com.zhang.walmartassessment.domain.usecase

import com.zhang.walmartassessment.domain.repository.ICountryRepository

/**
 * Use case for retrieving the list of countries from the repository.
 *
 * Acts as an abstraction layer between the UI/ViewModel and the data repository,
 * encapsulating the logic to fetch country data.
 *
 * @property countryRepo An instance of [ICountryRepository] used to access country data.
 */
class CountryListUseCase(
    private val countryRepo: ICountryRepository
) {
    /**
     * Invokes the use case to fetch the country list from the API.
     *
     * @return A [Flow] emitting a [Result] containing a list of [Country] objects or an error.
     */
    suspend operator fun invoke() = countryRepo.getCountryListFromApi()
}
