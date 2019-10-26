package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.content.Intent

class SubsidiaryActivity : AppCompatActivity() {
    lateinit var tvName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subsidiary)
        tvName = findViewById(R.id.textView_subsidiary)
        val dataString: String? = intent.getStringExtra(Intent.EXTRA_TEXT)
        tvName.setText(dataString)
    }

    fun onClick(view: View) {
        val intent1 = Intent()
        setResult(RESULT_OK, intent1)
        finish()
    }
}
