package com.example.android.recycler_cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.response.WeatherResponse
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_city.*

class CityItemHolder(
    override val containerView: View,
    private val clickLambda: (WeatherResponse) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(weatherResponse: WeatherResponse) {
        city_name_tv.text = weatherResponse.name
        city_temp_tv.text = weatherResponse.main.temp.toString()
        val temp = weatherResponse.main.temp.toInt()
        var colorTemp = 0;
        when {
            temp < -30 -> colorTemp = R.color.tempVeryCold
            temp < -10 && temp >= -30 -> colorTemp = R.color.tempCold
            temp <= 10 && temp >= -10 -> colorTemp = R.color.tempCool
            temp in 11..30 -> colorTemp = R.color.tempWarm
            temp > 30 -> colorTemp = R.color.tempHot
        }
        city_temp_tv.setTextColor(getColor(containerView.context, colorTemp))
        itemView.setOnClickListener {
            clickLambda(weatherResponse)
        }

    }

    companion object {

        fun create(parent: ViewGroup, clickLambda: (WeatherResponse) -> Unit) =
            CityItemHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_city,
                    parent,
                    false
                ),
                clickLambda
            )
    }
}
