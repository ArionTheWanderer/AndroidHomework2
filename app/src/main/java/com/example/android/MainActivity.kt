package com.example.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.INVISIBLE
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.ProgressBar.VISIBLE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var pass: EditText

    object PasswordRepository {
        var password: String = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_sign_in_pass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ti_sign_in_pass.error = null
                ti_sign_in_pass.isErrorEnabled = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        pass = findViewById(R.id.et_sign_in_pass)
    }

    fun reset(view: View) {
        val intent = Intent(this,ResetActivity::class.java)
        progressBar.setVisibility(VISIBLE)
        startActivity(intent)
        progressBar.setVisibility(INVISIBLE)
    }

    private fun setPasswordError() {
        ti_sign_in_pass.error = getString(R.string.validate_password)
    }

    fun login(view: View) {
        val pass_to_string: String? = pass.text.toString()
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        if (pass_to_string == PasswordRepository.password) {
            val intent = Intent(this,HomeActivity::class.java)
            progressBar.setVisibility(VISIBLE)
            startActivity(intent)
            progressBar.setVisibility(INVISIBLE)
        } else {
            progressBar.setVisibility(VISIBLE)
            setPasswordError();
            progressBar.setVisibility(INVISIBLE)
        }

    }

}
