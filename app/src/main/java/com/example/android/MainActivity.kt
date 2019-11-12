package com.example.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var adapter: CityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = CityAdapter(getDataSource()) { name, desc, img ->
            navigateToDescription(name, desc, img)
        }

        rv_cities.adapter = adapter
    }

    private fun navigateToDescription(name: String, desc: String, img: Int) {
        startActivity(DescActivity.createIntent(this, name, desc, img))
    }

    private fun getDataSource(): List<City> = arrayListOf(
        City(
            "Kazan",
            "This is the capital and largest city of the Republic of Tatarstan, Russia.",
            R.drawable.all_kazan
        ),
        City(
            "New York",
            "This is the most populous city in the United States.",
            R.drawable.all_new_york
        ),
        City(
            "Kazan",
            "This is the capital and largest city of the Republic of Tatarstan, Russia.",
            R.drawable.all_kazan
        ),
        City(
            "New York",
            "This is the most populous city in the United States.",
            R.drawable.all_new_york
        ),
        City(
            "Kazan",
            "This is the capital and largest city of the Republic of Tatarstan, Russia.",
            R.drawable.all_kazan
        ),
        City(
            "New York",
            "This is the most populous city in the United States.",
            R.drawable.all_new_york
        ),
        City(
            "Kazan",
            "This is the capital and largest city of the Republic of Tatarstan, Russia.",
            R.drawable.all_kazan
        ),
        City(
            "New York",
            "This is the most populous city in the United States.",
            R.drawable.all_new_york
        ),
        City(
            "Kazan",
            "This is the capital and largest city of the Republic of Tatarstan, Russia.",
            R.drawable.all_kazan
        ),
        City(
            "New York",
            "This is the most populous city in the United States.",
            R.drawable.all_new_york
        )
    )
}
