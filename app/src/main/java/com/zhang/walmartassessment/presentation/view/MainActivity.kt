package com.zhang.walmartassessment.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhang.walmartassessment.App
import com.zhang.walmartassessment.data.repository.RepositoryImpl
import com.zhang.walmartassessment.data.api.ApiService
import com.zhang.walmartassessment.data.util.NetworkMonitor
import com.zhang.walmartassessment.data.util.Result
import com.zhang.walmartassessment.databinding.ActivityMainBinding
import com.zhang.walmartassessment.domain.usecase.CountryListUseCase
import com.zhang.walmartassessment.presentation.viewmodel.CountryViewModel
import com.zhang.walmartassessment.presentation.viewmodel.CountryViewModelFactory
import kotlinx.coroutines.launch

/**
 * MainActivity to display a list of countries.
 * This activity manages the UI for displaying country data
 * and handles network connectivity changes and UI updates.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var countryListAdapter: CountryListAdapter

    // ViewModel instance to handle country list data
    private val viewModel: CountryViewModel by viewModels {
        val apiService = ApiService.getInstance()
        val repository = RepositoryImpl(apiService)
        val useCase = CountryListUseCase(repository)
        CountryViewModelFactory(useCase)
    }

    /**
     * Saves the RecyclerView scroll position when the activity is paused.
     */
    override fun onPause() {
        super.onPause()
        viewModel.scrollPosition = (binding.rvCountries.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
    }

    /**
     * Restores the RecyclerView scroll position when the activity is resumed.
     */
    override fun onResume() {
        super.onResume()
        (binding.rvCountries.layoutManager as LinearLayoutManager)
            .scrollToPosition(viewModel.scrollPosition)
    }

    /**
     * Called when the activity is created.
     * Initializes the UI components and sets up necessary listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    /**
     * Initializes the views and sets up the network monitoring and RecyclerView adapter.
     */
    private fun initViews() {
        networkMonitor = (application as App).networkMonitor

        // Set up RecyclerView with LinearLayoutManager and the adapter
        binding.rvCountries.layoutManager = LinearLayoutManager(this)
        countryListAdapter = CountryListAdapter()
        binding.rvCountries.adapter = countryListAdapter

        lifecycleScope.launch {
            // Check network connection and trigger API call if connected
            if (networkMonitor.isConnected.value) {
                viewModel.getCountryList()
            }

            // Observe network connection changes
            launch {
                networkMonitor.isConnected.collect { isConnected ->
                    if (isConnected && viewModel.countryListState.value is Result.Empty) {
                        viewModel.getCountryList()
                    }

                    // Show "No Connection" message if no data is fetched and network is disconnected
                    binding.txtNoConnection.visibility =
                        if (isConnected || viewModel.countryListState.value is Result.Success) View.GONE
                        else View.VISIBLE
                }
            }

            // Observe country list state and update the UI accordingly
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.countryListState.collect { result ->
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                countryListAdapter.updateData(result.data)
                            }

                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(this@MainActivity, result.exception.message, Toast.LENGTH_LONG).show()
                            }

                            is Result.Empty -> {
                                Toast.makeText(this@MainActivity, "Loading...", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
