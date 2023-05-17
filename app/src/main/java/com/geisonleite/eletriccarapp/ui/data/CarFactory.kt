package com.geisonleite.eletriccarapp.ui.data

import com.geisonleite.eletriccarapp.ui.domain.Car

object CarFactory {
    val list = listOf(
        Car(
            id = 1,
            price = "100",
            battery = "300 kWh",
            power = "100 cv",
            recharge = "20 min",
            urlPhoto = "teste"
        ),
        Car(
            id = 2,
            price = "200",
            battery = "400 kWh",
            power = "200 cv",
            recharge = "40 min",
            urlPhoto = "teste2"
        )
    )
}