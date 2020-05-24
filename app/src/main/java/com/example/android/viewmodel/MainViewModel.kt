package com.example.android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.OtherConstants
import com.example.android.WeatherService
import com.example.android.response.WeatherResponse
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.*
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel(), CoroutineScope by MainScope() {

    @Inject
    @Suppress("LateinitUsage")
    lateinit var service: WeatherService

    @Inject
    @Suppress("LateinitUsage")
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var wayLatitude: Double? = 0.0
    private var wayLongitude: Double? = 0.0

    var citiesData = MutableLiveData<List<WeatherResponse>>()

    fun getNearlyCities() {
        if (citiesData.value == null || citiesData.value?.isEmpty() == true) {
            viewModelScope.launch {
                if (mFusedLocationClient.lastLocation.isComplete) {
                    setLocation()
                    delay(OtherConstants.delay)
                    val response = withContext(Dispatchers.IO) {
                        service.weatherInNearbyCities(
                            wayLongitude ?: 0.0,
                            wayLatitude ?: 0.0,
                            OtherConstants.numberOfCities
                        )
                    }
                    citiesData.postValue(response.body()?.list)
                }
            }
        }
    }

    fun setLocation() {
        mFusedLocationClient.lastLocation.also {
            wayLatitude = it.result?.latitude
            wayLongitude = it.result?.longitude
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}
