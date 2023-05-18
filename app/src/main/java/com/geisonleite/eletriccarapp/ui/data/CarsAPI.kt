package com.geisonleite.eletriccarapp.ui.data

import com.geisonleite.eletriccarapp.ui.domain.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsAPI {
    @GET("cars.json")
    fun getAllCars():Call<List<Car>>
}