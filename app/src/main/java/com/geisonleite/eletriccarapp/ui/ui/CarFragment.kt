package com.geisonleite.eletriccarapp.ui.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.geisonleite.eletriccarapp.R
import com.geisonleite.eletriccarapp.ui.data.CarFactory
import com.geisonleite.eletriccarapp.ui.domain.Car
import com.geisonleite.eletriccarapp.ui.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class CarFragment : Fragment(){
    lateinit var fabCalculate: FloatingActionButton
    lateinit var listCars: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
        setupListeners()
    }

    fun setupView(view: View) {
        view.apply {
            fabCalculate = findViewById(R.id.fab_calculate)
            listCars = findViewById(R.id.rv_list_cars)
        }
    }

    fun setupList(){
        val adapter = CarAdapter(CarFactory.list)
        listCars.adapter = adapter
    }

    fun setupListeners() {
        fabCalculate.setOnClickListener {
            //startActivity(Intent(context, CalculateAutonomyActivity::class.java))
            MyTask().execute("https://geisonsile.github.io/cars_api/cars.json")
        }
    }

    inner class MyTask: AsyncTask<String, String, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("My Task", "Iniciando...")
        }

        override fun doInBackground(vararg url: String?): String {
            var urlConnection: HttpsURLConnection? = null
            try {
                val urlBase = URL(url[0])
                urlConnection = urlBase.openConnection() as HttpsURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000

                var response = urlConnection.inputStream.bufferedReader().use { it.readText() }

                publishProgress(response)

            }catch (ex: Exception){
                Log.e("Erro", "Erro ao realizar processamento....")
            }finally {
                urlConnection?.disconnect()
            }

            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()){
                    val id = jsonArray.getJSONObject(i).getString("id")
                    Log.d("ID ->", id)

                    val price = jsonArray.getJSONObject(i).getString("price")
                    Log.d("Price ->", price)

                    val battery = jsonArray.getJSONObject(i).getString("battery")
                    Log.d("Baterry->", battery)

                    val power = jsonArray.getJSONObject(i).getString("power")
                    Log.d("Power ->", power)

                    val recharge = jsonArray.getJSONObject(i).getString("recharge")
                    Log.d("Recharge ->", recharge)

                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")
                    Log.d("UrlPhoto ->", urlPhoto)

                    val model = Car(
                        id = id.toInt(),
                        price = price,
                        battery = battery,
                        power = power,
                        recharge = recharge,
                        urlPhoto = urlPhoto
                    )
                    Log.d("Model ->", model.toString())
                }

            }catch (ex:Exception){
                Log.e("Erro ->", ex.message.toString())
            }
        }
    }
}