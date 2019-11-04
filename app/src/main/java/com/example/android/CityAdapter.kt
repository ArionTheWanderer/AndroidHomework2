package com.example.android

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(
    private var dataSource: List<City>,
    private val clickLambda: (String, String, Int) -> Unit
) : RecyclerView.Adapter<CityItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemHolder =
        CityItemHolder.create(parent, clickLambda)

    override fun onBindViewHolder(holder: CityItemHolder, position: Int) {
        holder.bind(dataSource[position])
    }

    override fun getItemCount() = dataSource.size
}