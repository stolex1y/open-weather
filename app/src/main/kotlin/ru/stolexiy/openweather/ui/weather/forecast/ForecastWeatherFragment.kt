package ru.stolexiy.openweather.ui.weather.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.stolexiy.openweather.R
import ru.stolexiy.openweather.databinding.FragmentForecastWeatherBinding
import ru.stolexiy.openweather.ui.util.BindingDelegate.Companion.bindingDelegate

@AndroidEntryPoint
class ForecastWeatherFragment : Fragment() {
    private val binding by bindingDelegate<FragmentForecastWeatherBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_forecast_weather, container, false)
    }
}
