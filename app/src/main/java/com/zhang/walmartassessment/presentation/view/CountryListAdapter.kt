package com.zhang.walmartassessment.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhang.walmartassessment.databinding.ViewHolderCountryBinding
import com.zhang.walmartassessment.domain.dto.Country

/**
 * Recyclerview Adapter
 */
class CountryListAdapter(
    private var countryList: List<Country> = emptyList()
) : RecyclerView.Adapter<CountryListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderCountryBinding.inflate(inflater, parent, false)
        return CountryListViewHolder(binding)
    }

    override fun getItemCount() = countryList.size

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.setData(countryList[position])
    }

    fun updateData(newList: List<Country>) {
        countryList = newList
        notifyDataSetChanged()
    }
}