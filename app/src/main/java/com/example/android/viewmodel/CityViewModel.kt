package com.example.android.viewmodel

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.R
import com.example.android.TempConstants
import com.example.android.WeatherService
import com.example.android.WindConstants
import com.example.android.recycler_city.DataPair
import com.example.android.response.WeatherResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

class CityViewModel @Inject constructor() : ViewModel(), CoroutineScope by MainScope() {
    @Inject
    @Suppress("LateinitUsage")
    lateinit var service: WeatherService

    @Inject
    @Suppress("LateinitUsage")
    lateinit var picasso: Picasso

    var recData = MutableLiveData<List<DataPair>>()

    var actionBarData = MutableLiveData<WeatherResponse>()

    var weatherPic = MutableLiveData<ImageView>()

    var colorTemp = MutableLiveData<Int>()

    fun getDetailedInfo(cityId: Int, context: Context) {
        if (recData.value == null || actionBarData.value == null) {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    service.weatherById(cityId)
                }
                setValues(response, context)
            }
        }
    }

    fun setValues(response: WeatherResponse, context: Context) {
        val list: LinkedList<DataPair> = LinkedList()
        list.add(DataPair("Pressure", response.main.pressure.toString() + " hpa"))
        list.add(DataPair("Wind speed", response.wind.speed.toString() + " m/s"))
        var windOrientation = ""
        val deg = response.wind.deg
        when {
            deg < WindConstants.thirty || deg >= WindConstants.threeHundred30 -> windOrientation =
                "North"
            deg < WindConstants.sixty -> windOrientation = "Northeast"
            deg < WindConstants.hundred20 -> windOrientation = "East"
            deg < WindConstants.hundred50 -> windOrientation = "Southeast"
            deg < WindConstants.twoHundred10 -> windOrientation = "South"
            deg < WindConstants.twoHundred40 -> windOrientation = "Southwest"
            deg < WindConstants.threeHundred -> windOrientation = "West"
            deg < WindConstants.threeHundred30 -> windOrientation = "Northwest"
        }
        list.add(DataPair("Wind orientation", windOrientation))
        list.add(DataPair("Humidity", response.main.humidity.toString() + "%"))
        list.add(DataPair("Clouds", response.weathers[0].description))
        list.add(DataPair("Max temperature", response.main.tempMax.toString() + " ℃"))
        list.add(DataPair("Min temperature", response.main.tempMin.toString() + " ℃"))
        val picUrl = "https://openweathermap.org/img/wn/${response.weathers[0].icon}@2x.png"
        val picture = ImageView(context)
        picasso.load(picUrl).into(picture)
        val temp = response.main.temp.toInt()
        var colorTempLocal = 0
        when {
            temp < TempConstants.minus30 -> colorTempLocal = R.color.tempVeryCold
            temp in TempConstants.minus30 until TempConstants.minus10 -> colorTempLocal =
                R.color.tempCold
            temp in TempConstants.minus10..TempConstants.plus10 -> colorTempLocal = R.color.tempCool
            temp in TempConstants.plus10 + 1 until TempConstants.plus30 -> colorTempLocal =
                R.color.tempWarm
            temp > TempConstants.plus30 -> colorTempLocal = R.color.tempHot
        }
        postValues(picture, colorTempLocal, list, response)
    }

    fun postValues(
        picture: ImageView,
        colorTempLocal: Int,
        list: LinkedList<DataPair>,
        response: WeatherResponse
    ) {
        weatherPic.postValue(picture)
        colorTemp.postValue(colorTempLocal)
        recData.postValue(list)
        actionBarData.postValue(response)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}
