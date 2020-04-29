package com.example.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.android.recycler_city.CityDetAdapter
import com.example.android.recycler_city.DataPair
import com.example.android.response.WeatherResponse
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.coroutines.*
import java.util.*

class CityActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var service: WeatherService

    companion object {
        const val CITY_ID = "cityId"

        fun createIntent(activity: Activity, cityId: Int) =
            Intent(activity, CityActivity::class.java).apply {
                putExtra(
                    CITY_ID, cityId
                )
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        service = ApiFactory.weatherService

        launch {
            val response = withContext(Dispatchers.IO) {
                service.weatherById(intent.extras?.getInt(CITY_ID) ?: 0)
            }

            city_detailed_name_tv.text = response.name
            status_detailed_tv.text = response.weathers[0].main
            temp_detailed_tv.text = response.main.temp.toString() + " ℃"
            feels_like_detailed_tv.text = response.main.feelsLike.toString() + " ℃"

            val picUrl = "https://openweathermap.org/img/wn/${response.weathers[0].icon}@2x.png"
            Glide
                .with(this@CityActivity)
                .load(picUrl)
                .into(weather_pic)
            val temp = response.main.temp.toInt()
            var colorTemp = 0;
            when {
                temp < -30 -> colorTemp = R.color.tempVeryCold
                temp < -10 && temp >= -30 -> colorTemp = R.color.tempCold
                temp <= 10 && temp >= -10 -> colorTemp = R.color.tempCool
                temp in 11..30 -> colorTemp = R.color.tempWarm
                temp > 30 -> colorTemp = R.color.tempHot
            }

            temp_detailed_tv.setTextColor(getColor(colorTemp))
            setRecInfo(response)
        }
    }

    private fun setRecInfo(response: WeatherResponse) {
        var list: LinkedList<DataPair> = LinkedList()
        list.add(DataPair("Pressure", response.main.pressure.toString() + " hpa"))
        list.add(DataPair("Wind speed", response.wind.speed.toString() + " m/s"))
        var windOrientation = ""
        val x = response.wind.deg
        when {
            x < 30 || x >= 330 -> windOrientation = "North"
            x < 60 -> windOrientation = "Northeast"
            x < 120 -> windOrientation = "East"
            x < 150 -> windOrientation = "Southeast"
            x < 210 -> windOrientation = "South"
            x < 240 -> windOrientation = "Southwest"
            x < 300 -> windOrientation = "West"
            x < 330 -> windOrientation = "Northwest"
        }
        list.add(DataPair("Wind orientation", windOrientation))
        list.add(DataPair("Humidity", response.main.humidity.toString() + "%"))
        list.add(DataPair("Clouds", response.weathers[0].description))
        list.add(DataPair("Max temperature", response.main.tempMax.toString() + " ℃"))
        list.add(DataPair("Min temperature", response.main.tempMin.toString() + " ℃"))

        city_rec.adapter = CityDetAdapter(list)
    }

}
