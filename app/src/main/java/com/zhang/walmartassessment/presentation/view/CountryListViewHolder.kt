package com.zhang.walmartassessment.presentation.view

import androidx.recyclerview.widget.RecyclerView
import com.zhang.walmartassessment.R
import com.zhang.walmartassessment.databinding.ViewHolderCountryBinding
import com.zhang.walmartassessment.domain.dto.Country

/**
 * ViewHolder class for displaying country data in a RecyclerView.
 *
 * Binds the UI elements defined in [ViewHolderCountryBinding] with the [Country] data model.
 *
 * Responsibilities:
 * - Sets country code, capital, and a formatted name-region string in the respective views.
 * - Uses a string resource [R.string.name_region_format] to maintain localization and formatting.
 *
 * @param binding The view binding object for the country item layout.
 */
class CountryListViewHolder(
    private val binding: ViewHolderCountryBinding
) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Populates the ViewHolder's views with the given country data.
     *
     * @param country The country object to bind to the UI.
     */
    fun setData(country: Country) {
        with(binding) {
            txtCode.text = country.code
            txtCapital.text = country.capital
            txtNameRegion.text = itemView.resources.getString(
                R.string.name_and_region,
                country.name, country.region
            )
        }
    }
}
