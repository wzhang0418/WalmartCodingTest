package com.zhang.walmartassessment.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zhang.walmartassessment.data.api.ApiService
import com.zhang.walmartassessment.data.model.CountryDetail
import com.zhang.walmartassessment.data.util.Result
import com.zhang.walmartassessment.domain.dto.Country
import com.zhang.walmartassessment.domain.repository.ICountryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CountryRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var countryRepository: ICountryRepository
    private lateinit var testDispatcher: TestDispatcher

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // Mocking the ApiService and setting up dispatcher for test
        apiService = mockk()
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    /**
     * Test case to verify successful fetching of country list from API with multiple countries.
     */
    @Test
    fun `getCountryListFromApi returns success with multiple countries`() = runTest(testDispatcher) {
        val fakeCountryList = listOf(
            CountryDetail(
                capital = "Washington, D.C.",
                code = "US",
                name = "United States",
                region = "Americas"
            ),
            CountryDetail(
                capital = "Ottawa",
                code = "CA",
                name = "Canada",
                region = "Americas"
            ),
            CountryDetail(
                capital = "Berlin",
                code = "DE",
                name = "Germany",
                region = "Europe"
            ),
            CountryDetail(
                capital = "Tokyo",
                code = "JP",
                name = "Japan",
                region = "Asia"
            )
        )
        val fakeSuccessResponse: Response<List<CountryDetail>> = Response.success(fakeCountryList)
        val expectedDomainResult = listOf(
            Country(
                capital = "Washington, D.C.",
                code = "US",
                name = "United States",
                region = "Americas"
            ),
            Country(
                capital = "Ottawa",
                code = "CA",
                name = "Canada",
                region = "Americas"
            ),
            Country(
                capital = "Berlin",
                code = "DE",
                name = "Germany",
                region = "Europe"
            ),
            Country(
                capital = "Tokyo",
                code = "JP",
                name = "Japan",
                region = "Asia"
            )
        )

        // Setting up repository with mocked ApiService
        countryRepository = RepositoryImpl(apiService)
        coEvery { apiService.getCountryList() } returns fakeSuccessResponse

        // Collect the result as Flow
        val responseFlow = countryRepository.getCountryListFromApi().toList()
        advanceUntilIdle()

        // Verify loading and success result
        assert(responseFlow[0] is Result.Loading)
        assert(responseFlow[1] is Result.Success)
        val successResult = responseFlow[1] as Result.Success
        assert(successResult.data == expectedDomainResult)
    }

    /**
     * Test case to verify error handling when network fails during API call.
     */
    @Test
    fun `getCountryListFromApi returns error on network failure`() = runTest(testDispatcher) {
        val networkError = IOException("Network error")

        // Mocking API to throw IOException
        coEvery { apiService.getCountryList() } throws networkError

        countryRepository = RepositoryImpl(apiService)

        // Collect the result as Flow
        val responseFlow = countryRepository.getCountryListFromApi().toList()
        advanceUntilIdle()

        // Verify loading and error result
        assert(responseFlow[0] is Result.Loading)
        assert(responseFlow[1] is Result.Error)
        val errorResult = responseFlow[1] as Result.Error
        assert(errorResult.exception is IOException)
    }

    /**
     * Test case to verify error handling when API response is unsuccessful (HTTP error).
     */
    @Test
    fun `getCountryListFromApi returns error on unsuccessful response`() = runTest(testDispatcher) {
        val fakeErrorResponse: Response<List<CountryDetail>> = Response.error(
            404, "Internal Server Error".toResponseBody(null)
        )

        // Mocking API response to return an error response
        coEvery { apiService.getCountryList() } returns fakeErrorResponse
        countryRepository = RepositoryImpl(apiService)

        // Collect the result as Flow
        val responseFlow = countryRepository.getCountryListFromApi().toList()
        advanceUntilIdle()

        // Verify loading and error result with error message
        assert(responseFlow[0] is Result.Loading)
        assert(responseFlow[1] is Result.Error)
        val errorResult = responseFlow[1] as Result.Error
        assert(errorResult.exception.message == "Network call failed: 404")
    }

    /**
     * Test case to verify error handling when the API response body is empty.
     */
    @Test
    fun `getCountryListFromApi returns error on empty response body`() = runTest(testDispatcher) {
        val fakeEmptyResponse: Response<List<CountryDetail>> = Response.success(null)

        // Mocking API response to return an empty body
        coEvery { apiService.getCountryList() } returns fakeEmptyResponse
        countryRepository = RepositoryImpl(apiService)

        // Collect the result as Flow
        val responseFlow = countryRepository.getCountryListFromApi().toList()
        advanceUntilIdle()

        // Verify loading and error result with specific empty response error
        assert(responseFlow[0] is Result.Loading)
        assert(responseFlow[1] is Result.Error)
        val errorResult = responseFlow[1] as Result.Error
        assert(errorResult.exception.message == "Empty response body")
    }

    @After
    fun tearDown() {
        // Resetting main dispatcher after tests
        Dispatchers.resetMain()
    }
}
