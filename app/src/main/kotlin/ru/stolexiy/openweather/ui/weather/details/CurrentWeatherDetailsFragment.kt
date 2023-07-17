package ru.stolexiy.openweather.ui.weather.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.databinding.FragmentCurrentWeatherDetailsBinding
import ru.stolexiy.openweather.ui.util.BindingDelegate.Companion.bindingDelegate
import ru.stolexiy.openweather.ui.util.CustomAbstractSavedStateViewModelFactory.Companion.assistedViewModels
import ru.stolexiy.openweather.ui.util.Formatters.formatAsCoordinates
import ru.stolexiy.openweather.ui.util.FragmentExtensions.repeatOnViewLifecycle
import ru.stolexiy.openweather.ui.util.FragmentExtensions.supportActionBar
import ru.stolexiy.openweather.ui.weather.current.CurrentWeatherFragmentToolbarProvider
import ru.stolexiy.openweather.ui.weather.current.CurrentWeatherViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CurrentWeatherDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: CurrentWeatherViewModel.Factory

    private val viewModel: CurrentWeatherViewModel by assistedViewModels(::requireActivity) { savedStateHandle ->
        viewModelFactory.create(savedStateHandle)
    }
    private val binding by bindingDelegate<FragmentCurrentWeatherDetailsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupTopToolbar()
        return inflater.inflate(R.layout.fragment_current_weather_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                startDataUpdating()
            }
        }
        observeData()
        observeState()
    }

    private fun observeState() {
        repeatOnViewLifecycle {
            viewModel.state.collect { state ->
                when (state) {
                    is CurrentWeatherViewModel.State.Error -> showError(state.error)
                    CurrentWeatherViewModel.State.Init -> startDataUpdating()
                    CurrentWeatherViewModel.State.Loaded -> stopLoading()
                    CurrentWeatherViewModel.State.Loading -> startLoading()
                }
            }
        }
    }

    private fun observeData() {
        repeatOnViewLifecycle {
            viewModel.data.collectLatest { data ->
                data.currentWeather?.toCurrentWeatherDetails(requireContext())
                    ?.let { currentWeather ->
                        binding.currentWeather = currentWeather
                        showCurrentLocation(currentWeather.location)
                    }
            }
        }
    }

    private fun showError(@StringRes error: Int) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
        stopLoading()
    }

    private fun startLoading() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun stopLoading() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun startDataUpdating() {
        viewModel.dispatchEvent(CurrentWeatherViewModel.Event.Load)
    }

    private fun setupTopToolbar() {
        supportActionBar?.show()
        val menuHost: MenuHost = requireActivity()
        val menuProvider: MenuProvider =
            CurrentWeatherFragmentToolbarProvider(requireContext(), this::startDataUpdating)
        menuHost.addMenuProvider(menuProvider, viewLifecycleOwner)
        menuHost.invalidateMenu()
    }

    private fun showCurrentLocation(location: Location) {
        supportActionBar?.title =
            location.city ?: (location.latitude to location.longitude).formatAsCoordinates()
    }
}
