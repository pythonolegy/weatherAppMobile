package com.example.main.presentation.fragments.detailsBottomSheetFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.main.R
import com.example.main.domain.models.LocationInfo
import com.example.main.domain.models.WeatherForecastInfo
import com.example.main.domain.models.WeatherInfo
import com.example.main.domain.usecases.GetWeatherForecastByLocationUseCase
import com.example.main.presentation.utils.NotificationUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val locationInfo: LocationInfo,
    private val getWeatherForecastByLocationUseCase: GetWeatherForecastByLocationUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val weatherForecastLiveMutable = MutableLiveData<WeatherForecastInfo?>()
    val weatherForecastLive: LiveData<WeatherForecastInfo?> = weatherForecastLiveMutable

    private val loadingLiveMutable = MutableLiveData(true)
    val loadingLive: LiveData<Boolean> = loadingLiveMutable

    private val errorLiveMutable = MutableLiveData<Int?>(null)
    val errorLive: LiveData<Int?> = errorLiveMutable

    init {
        NotificationUtils.createNotificationChannel(getApplication())
    }

    fun loadForecast() {
        loadingLiveMutable.postValue(true)
        errorLiveMutable.postValue(null)

        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                return@runCatching getWeatherForecastByLocationUseCase.execute(locationInfo)
            }.onSuccess { weatherForecast ->
                weatherForecastLiveMutable.postValue(weatherForecast)
                weatherForecast?.let {
                    val weatherInfo = it.list.firstOrNull()
                    weatherInfo?.let {
                        val temperature = weatherInfo.temperatureInCelsius
                        Log.d("DetailsViewModel", "Sending notification for $temperature°C")
                        NotificationUtils.sendWeatherNotification(getApplication(), "$temperature°C")
                    }
                }
            }.onFailure {
                errorLiveMutable.postValue(when (it) {
                    is UnknownHostException -> R.string.network_unavailable
                    else -> null
                })
            }.also {
                loadingLiveMutable.postValue(false)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(locationInfo: LocationInfo): DetailsViewModel
    }

    companion object {
        const val ASSISTED_LOCATION_INFO_KEY = "LOCATION_INFO"
    }
}
