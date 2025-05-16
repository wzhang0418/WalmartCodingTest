package com.zhang.walmartassessment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhang.walmartassessment.data.util.Result
import com.zhang.walmartassessment.domain.dto.Country
import com.zhang.walmartassessment.domain.usecase.CountryListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing the country list data and tracking UI and configuration changes.
 *
 * This ViewModel interacts with the [CountryListUseCase] to fetch the country data and exposes the result
 * as a state flow. It also handles UI-related logic such as scroll position and error handling.
 *
 * @param useCase The use case responsible for fetching the country list data.
 */
class CountryViewModel(
    private val useCase: CountryListUseCase
) : ViewModel() {

    /**
     * Private mutable state flow holding the current state of the country list data.
     * It can hold [Result] types such as [Result.Empty], [Result.Success], or [Result.Error].
     */
    private val _countryListState = MutableStateFlow<Result<List<Country>>>(Result.Empty)

    /**
     * Publicly exposed state flow for observing country list data changes.
     * This flow will emit the result of the country list request (success, error, or empty).
     */
    val countryListState: StateFlow<Result<List<Country>>> = _countryListState.asStateFlow()

    /**
     * Variable to keep track of the scroll position in the UI.
     * Used to remember the user's scroll position across configuration changes.
     */
    var scrollPosition: Int = 0

    /**
     * Coroutine exception handler that catches errors during the fetch operation.
     * It logs the error and updates the UI state to reflect the error.
     */
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _countryListState.value = Result.Error(exception as Exception)
    }

    /**
     * Initiates the process of fetching the country list data using the [CountryListUseCase].
     * Collects the data and updates the [countryListState] flow accordingly.
     */
    fun getCountryList() {
        viewModelScope.launch(coroutineExceptionHandler) {
            useCase().collect { result ->
                _countryListState.value = result
            }
        }
    }
}
