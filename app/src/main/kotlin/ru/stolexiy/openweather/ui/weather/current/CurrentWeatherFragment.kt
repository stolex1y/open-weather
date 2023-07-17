package ru.stolexiy.openweather.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.databinding.FragmentCurrentWeatherBinding
import ru.stolexiy.openweather.ui.util.BindingDelegate.Companion.bindingDelegate
import ru.stolexiy.openweather.ui.util.CustomAbstractSavedStateViewModelFactory.Companion.assistedViewModels
import ru.stolexiy.openweather.ui.util.FragmentExtensions.repeatOnViewLifecycle
import ru.stolexiy.openweather.ui.util.FragmentExtensions.supportActionBar
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: CurrentWeatherViewModel.Factory

    private val binding by bindingDelegate<FragmentCurrentWeatherBinding>()

    private val viewModel: CurrentWeatherViewModel by assistedViewModels(::requireActivity) { savedStateHandle ->
        viewModelFactory.create(
            savedStateHandle
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("create fragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("destroy view")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("destroy fragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("create view")
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTopToolbar()
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
                data.currentWeather?.toCurrentWeather(requireContext())?.let { currentWeather ->
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
        val menuHost: MenuHost = requireActivity()
        val menuProvider: MenuProvider =
            CurrentWeatherFragmentToolbarProvider(requireContext(), this::startDataUpdating)
        menuHost.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showCurrentLocation(location: Location) {
        supportActionBar?.title =
            location.city ?: location.coordinatesStr
    }
}
