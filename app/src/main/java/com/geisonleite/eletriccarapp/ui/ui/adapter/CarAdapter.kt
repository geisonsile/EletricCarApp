package com.geisonleite.eletriccarapp.ui.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geisonleite.eletriccarapp.R
import com.geisonleite.eletriccarapp.ui.domain.Car


class CarAdapter(private val cars: List<Car>, private val isFavoriteScreen: Boolean = false) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemLister: (Car) -> Unit = {}

    // Cria uma nova view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    // Pega o conteudo da view e troca pela informacao de item de uma lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = cars[position].price
        holder.battery.text = cars[position].battery
        holder.power.text = cars[position].power
        holder.recharge.text = cars[position].recharge

        if (isFavoriteScreen) {
            holder.favorite.setImageResource(R.drawable.ic_star_selected)
        }

        holder.favorite.setOnClickListener {
            val car = cars[position]
            carItemLister(car)
            setupFavorite(car, holder)
        }
    }

    private fun setupFavorite(car: Car, holder: ViewHolder) {
        car.isFavorite = !car.isFavorite
        if (car.isFavorite)
            holder.favorite.setImageResource(R.drawable.ic_star_selected)
        else
            holder.favorite.setImageResource(R.drawable.ic_star)
    }

    // Pega a quantidade de carros da lista
    override fun getItemCount(): Int = cars.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val power: TextView
        val recharge: TextView
        val favorite: ImageView

        init {
            view.apply {
                price = findViewById(R.id.tv_price_value)
                battery = findViewById(R.id.tv_battery_value)
                power = findViewById(R.id.tv_power_value)
                recharge = findViewById(R.id.tv_recharge_value)
                favorite = findViewById((R.id.iv_favorite))
            }

        }
    }

}