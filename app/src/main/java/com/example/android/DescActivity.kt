package com.example.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_desc.*

class DescActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc)
        val name = intent?.extras?.getString(KEY_NAME) ?: "DEFAULT NAME"
        val desc = intent?.extras?.getString(KEY_DESC) ?: "DEFAULT DESC"
        val img = intent?.extras?.getInt(KEY_IMG) ?: R.mipmap.ic_launcher
        tv_desc_city_name.text = name
        tv_desc_city_desc.text = desc
        iv_desc_city.setImageResource(img)
    }

    companion object {

        private const val KEY_NAME = "name"
        private const val KEY_DESC = "desc"
        private const val KEY_IMG = "img"

        fun createIntent(activity: Activity, name: String, desc: String, img: Int) =
            Intent(activity, DescActivity::class.java).apply {
                putExtra(KEY_NAME, name)
                putExtra(KEY_DESC, desc)
                putExtra(KEY_IMG, img)
            }
    }
}