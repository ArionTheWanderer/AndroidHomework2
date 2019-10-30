package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import kotlinx.android.synthetic.main.activity_subsidiary.*

class SubsidiaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subsidiary)
        val dataString: String? = intent.getStringExtra(Intent.EXTRA_TEXT)
        tv_subsidiary.setText(dataString)
    }

    fun onClick(view: View) {
        setResult(RESULT_OK)
        finish()
    }
}