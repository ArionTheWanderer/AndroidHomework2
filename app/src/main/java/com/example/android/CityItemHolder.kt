package com.example.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_city.*

class CityItemHolder(
    override val containerView: View,
    private val clickLambda: (String, String, Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(city: City) {
        tv_city_name.text = city.name
        tv_city_desc.text = city.desc
        iv_city.setImageResource(city.image)
        itemView.setOnClickListener {
            clickLambda(city.name, city.desc, city.image)
        }
    }

    companion object {
        fun create(parent: ViewGroup, clickLambda: (String, String, Int) -> Unit) =
            CityItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false),
                clickLambda
            )
    }
}
