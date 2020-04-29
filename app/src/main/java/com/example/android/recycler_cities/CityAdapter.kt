package com.example.android.recycler_cities

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.response.WeatherResponse

class CityAdapter(
    private var dataSource: List<WeatherResponse>,
    private val clickLambda: (WeatherResponse) -> Unit
) : RecyclerView.Adapter<CityItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemHolder =
        CityItemHolder.create(parent, clickLambda)

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: CityItemHolder, position: Int) =
        holder.bind(dataSource[position])

    /*fun add(city: WeatherResponse) {
        val temp = dataSource.toMutableList()
        temp.add(4, book)
        temp.add(4, book.apply { title += " 2"})
        temp.add(4, book.apply { title += " 3"})
        dataSource = temp
        notifyItemRangeChanged(4, 4)
    }*/
}
