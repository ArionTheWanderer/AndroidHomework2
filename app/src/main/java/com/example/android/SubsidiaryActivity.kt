package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.content.Intent
import kotlinx.android.synthetic.main.activity_subsidiary.*

class SubsidiaryActivity : AppCompatActivity() {
    lateinit var tvName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subsidiary)
        tvName = textView_subsidiary
        val dataString: String? = intent.getStringExtra(Intent.EXTRA_TEXT)
        tvName.setText(dataString)
    }

    fun onClick(view: View) {
        setResult(RESULT_OK, Intent())
        finish()
    }
}