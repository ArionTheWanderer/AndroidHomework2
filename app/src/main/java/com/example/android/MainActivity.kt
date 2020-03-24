package com.example.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var service: WeatherService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = ApiFactory.weatherService

        launch {
            val response = withContext(Dispatchers.IO) {
                service.weatherByName("Moscow")
            }
            Snackbar.make(
                findViewById(android.R.id.content),
                response.main.toString(),
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }
}
