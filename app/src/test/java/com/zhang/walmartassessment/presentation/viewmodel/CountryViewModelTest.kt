package com.zhang.walmartassessment.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zhang.walmartassessment.data.util.Result
import com.zhang.walmartassessment.domain.dto.Country
import com.zhang.walmartassessment.domain.usecase.CountryListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {
    private lateinit var viewModel: CountryViewModel
    private lateinit var useCase: CountryListUseCase
    private lateinit var testDispatcher: TestDispatcher

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        useCase = mockk()
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    /**
     * Test case to verify successful fetching of multiple countries.
     */
    @Test
    fun `getCountryList update countryListState successfully with multiple countries`() = runTest(testDispatcher) {
        val fakeDomainList = listOf(
            Country(
                name = "United States of America",
                region = "NA", code = "US", capital = "Washington, D.C."
            ),
            Country(
                name = "Uruguay",
                region = "SA", code = "UY", capital = "Montevideo"
            ),
            Country(
                name = "Germany",
                region = "EU", code = "DE", capital = "Berlin"
            ),
            Country(
                name = "Japan",
                region = "AS", code = "JP", capital = "Tokyo"
            ),
            Country(
                name = "Australia",
                region = "OCE", code = "AU", capital = "Canberra"
            )
        )
        val fakeSuccessFlow = flow {
            emit(Result.Loading)
            delay(100L)
            emit(Result.Success(fakeDomainList))
        }
        coEvery { useCase() } returns fakeSuccessFlow

        viewModel = CountryViewModel(useCase)
        viewModel.getCountryList()
        val stateList = mutableListOf<Result<List<Country>>>()
        val job = launch {
            viewModel.countryListState.collect {
                stateList.add(it)
            }
        }
        advanceUntilIdle()
        job.cancel()

        assert(stateList[0] is Result.Loading)
        assert(stateList[1] is Result.Success)
        val successResult = stateList[1] as Result.Success
        assert(successResult.data == fakeDomainList)
    }

    /**
     * Test case to verify failed fetching of countries due to API issue.
     */
    @Test
    fun `getCountryList failed due to api issue`() = runTest(testDispatcher) {
        val fakeException = Exception("Network Error")
        val fakeErrorFlow = flow {
            emit(Result.Loading)
            delay(100L)
            emit(Result.Error(fakeException))
        }
        coEvery { useCase() } returns fakeErrorFlow

        viewModel = CountryViewModel(useCase)
        viewModel.getCountryList()
        val stateList = mutableListOf<Result<List<Country>>>()
        val job = launch {
            viewModel.countryListState.collect {
                stateList.add(it)
            }
        }
        advanceUntilIdle()
        job.cancel()

        assert(stateList[0] is Result.Loading)
        assert(stateList[1] is Result.Error)
        val errorResult = stateList[1] as Result.Error
        assert(errorResult.exception == fakeException)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
