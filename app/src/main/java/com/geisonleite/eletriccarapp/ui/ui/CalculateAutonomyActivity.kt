package com.geisonleite.eletriccarapp.ui.ui

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.geisonleite.eletriccarapp.R
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class CalculateAutonomyActivity : AppCompatActivity() {
    lateinit var price: EditText
    lateinit var kmTraveled: EditText
    lateinit var btnCalculate: Button
    lateinit var result: TextView
    lateinit var btnClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_autonomy)

        setupView()
        setupListeners()
        setupCachedResult()
    }

    private fun setupCachedResult() {
        result.text = getSharedPref().toString()
    }

    fun setupView() {
        price = findViewById(R.id.et_price_kwh)
        kmTraveled = findViewById(R.id.et_km_traveled)
        result = findViewById(R.id.tv_result)
        btnCalculate = findViewById(R.id.btn_calculate)
        btnClose = findViewById(R.id.iv_close)
    }

    fun setupListeners() {
        btnCalculate.setOnClickListener {
            calcular()
        }

        btnClose.setOnClickListener {
            finish()
        }
    }

    fun calcular() {
        val price = price.text.toString().toFloat()
        val km = kmTraveled.text.toString().toFloat()
        val resultCalculate = price / km

        result.text = resultCalculate.toString()

        saveSharedPref(resultCalculate)
    }

    fun saveSharedPref(result: Float) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putFloat(getString(R.string.saved_calc), result)
            apply()
        }
    }

    fun getSharedPref(): Float {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.saved_calc), 0.0f)
    }
}