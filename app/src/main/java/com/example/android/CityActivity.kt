package com.example.android

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.recycler_city.CityDetAdapter
import com.example.android.recycler_city.DataPair
import com.example.android.viewmodel.CityViewModel
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.util.*
import javax.inject.Inject

class CityActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    @Inject
    @Suppress("LateinitUsage")
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Suppress("LateinitUsage")
    lateinit var cityViewModel: CityViewModel

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
        MyApp().plusSscComponent(this).inject(this)
        cityViewModel = ViewModelProvider(this, viewModelFactory)[CityViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        observeCity()

    }

    private fun observeCity() {
        cityViewModel.recData.observe(this, androidx.lifecycle.Observer {
            city_rec.adapter = CityDetAdapter(it ?: LinkedList<DataPair>())
        })
        cityViewModel.actionBarData.observe(this, androidx.lifecycle.Observer {
            city_detailed_name_tv.text = it.name
            status_detailed_tv.text = it.weathers[0].main
            temp_detailed_tv.text = it.main.temp.toString() + " ℃"
            feels_like_detailed_tv.text = it.main.feelsLike.toString() + " ℃"
        })
        cityViewModel.weatherPic.observe(this, androidx.lifecycle.Observer {
            val drawable: Drawable? = it.drawable
            weather_pic.setImageDrawable(drawable)
        })
        cityViewModel.colorTemp.observe(this, androidx.lifecycle.Observer {
            temp_detailed_tv.setTextColor(getColor(it))
        })
        cityViewModel.getDetailedInfo(intent.extras?.getInt(CITY_ID) ?: 0, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApp().clearSSCComponent()
    }
}
