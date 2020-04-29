package com.example.android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.android.recycler_cities.CityAdapter
import com.example.android.response.WeatherResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope(),
    SearchView.OnQueryTextListener {

    companion object {
        const val MY_PERMISSIONS_REQUEST_LOCATION: Int = 1234
        private var wayLatitude: Double = 0.0
        private var wayLongitude: Double = 0.0
    }

    private lateinit var service: WeatherService

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (toolbar as Toolbar).also {
            setSupportActionBar(it)
        }

        service = ApiFactory.weatherService

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            setLocation()
            launch {
                delay(1000)
                val response = withContext(Dispatchers.IO) {
                    service.weatherInNearbyCities(
                        wayLongitude,
                        wayLatitude,
                        20
                    )
                }
                if ((response.body()?.list?.size) != 1) {
                    rv_cities.adapter =
                        CityAdapter(response.body()?.list ?: LinkedList<WeatherResponse>())
                        { weatherResponse ->
                            startActivity(
                                CityActivity.createIntent(
                                    this@MainActivity,
                                    weatherResponse.id
                                )
                            )
                        }
                }
            }
        }
    }

    private fun setLocation() {
        mFusedLocationClient.lastLocation.addOnSuccessListener(this) {
            it?.let {
                wayLatitude = it.latitude
                wayLongitude = it.longitude
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    setLocation()
                    launch {
                        delay(1000)
                        val response = withContext(Dispatchers.IO) {
                            service.weatherInNearbyCities(
                                wayLongitude,
                                wayLatitude,
                                20
                            )
                        }
                        if (response.isSuccessful) {
                            rv_cities.adapter =
                                CityAdapter(response.body()?.list ?: LinkedList<WeatherResponse>())
                                { weatherResponse ->
                                    startActivity(
                                        CityActivity.createIntent(
                                            this@MainActivity,
                                            weatherResponse.id
                                        )
                                    )
                                }
                        }
                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "No location permission",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                return
            }
            else -> {
                return
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Enter the name of the city"
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        service.weatherByName(query)
                    }
                    val intent = Intent(this@MainActivity, CityActivity::class.java)
                    intent.putExtra(CityActivity.CITY_ID, response.id)
                    startActivity(intent)
                } catch (e: retrofit2.HttpException) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "No such city",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}
