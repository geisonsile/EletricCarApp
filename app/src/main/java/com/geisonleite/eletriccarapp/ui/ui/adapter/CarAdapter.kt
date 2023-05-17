package com.geisonleite.eletriccarapp.ui.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geisonleite.eletriccarapp.R
import com.geisonleite.eletriccarapp.ui.domain.Car


class CarAdapter(private val cars: List<Car>) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

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
    }

    // Pega a quantidade de carros da lista
    override fun getItemCount(): Int = cars.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val power: TextView
        val recharge: TextView

        init {
            view.apply {
                price = findViewById(R.id.tv_preco_value)
                battery = findViewById(R.id.tv_battery_value)
                power = findViewById(R.id.tv_power_value)
                recharge = findViewById(R.id.tv_recharge_value)
            }

        }
    }

}