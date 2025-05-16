package com.zhang.walmartassessment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhang.walmartassessment.domain.usecase.CountryListUseCase

/**
 * track instance of viewmodel
 */
class CountryViewModelFactory(
    private val useCase: CountryListUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            return CountryViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class!")
    }
}