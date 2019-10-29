package com.example.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var etName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etName = et_main_data
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("myLogs", "requestCode = $requestCode, resultCode = $resultCode")
        if (requestCode == REQUEST_CODE_SHARE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Shared", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Is Failed message", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClick(view: View) {
        val textData: String? = etName.text.toString()
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textData)
            type = "text/plain"
        }
        startActivityForResult(sendIntent, REQUEST_CODE_SHARE)
    }

    companion object {
        private const val REQUEST_CODE_SHARE: Int = 1
    }
}