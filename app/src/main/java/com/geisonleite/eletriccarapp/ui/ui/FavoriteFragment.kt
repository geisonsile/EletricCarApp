package com.geisonleite.eletriccarapp.ui.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.geisonleite.eletriccarapp.R
import com.geisonleite.eletriccarapp.ui.data.local.CarRepository
import com.geisonleite.eletriccarapp.ui.domain.Car
import com.geisonleite.eletriccarapp.ui.ui.adapter.CarAdapter

class FavoriteFragment : Fragment() {

    lateinit var listCarsFavorites: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
    }

    private fun getCarsOnLocalDb(): List<Car> {
        val repository = CarRepository(requireContext())
        val carList = repository.getAll()
        Log.d("Lista de carros", carList.size.toString())
        return carList
    }


    fun setupView(view: View) {
        view.apply {
            listCarsFavorites = findViewById(R.id.rv_list_cars_favorites)
        }
    }

    fun setupList() {
        val cars = getCarsOnLocalDb()
        val carAdapter = CarAdapter(cars, isFavoriteScreen = true)
        listCarsFavorites.apply {
            isVisible = true
            adapter = carAdapter
        }
        carAdapter.carItemLister = { car ->
            // @TODO IMPLEMENTAR O DELETE NO BANCO DE DADOS
        }
    }
}