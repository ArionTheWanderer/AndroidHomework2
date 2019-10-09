package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class ResetActivity : AppCompatActivity() {
    lateinit var new_pass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
        new_pass = findViewById(R.id.et_reset_pass)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun click(view: View) {
        if(new_pass.text.toString() != null && !new_pass.text.toString().equals("")) {
            MainActivity.PasswordRepository.password = new_pass.text.toString()
            onBackPressed()
        }
    }
}
