package com.geisonleite.eletriccarapp.ui.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.geisonleite.eletriccarapp.ui.data.local.CarsContract.CarEntry.COLUMN_NAME_BATTERY
import com.geisonleite.eletriccarapp.ui.data.local.CarsContract.CarEntry.COLUMN_NAME_CAR_ID
import com.geisonleite.eletriccarapp.ui.data.local.CarsContract.CarEntry.COLUMN_NAME_POWER
import com.geisonleite.eletriccarapp.ui.data.local.CarsContract.CarEntry.COLUMN_NAME_PRICE
import com.geisonleite.eletriccarapp.ui.data.local.CarsContract.CarEntry.COLUMN_NAME_RECHARGE
import com.geisonleite.eletriccarapp.ui.data.local.CarsContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.geisonleite.eletriccarapp.ui.domain.Car

class CarRepository(private val context: Context) {

    fun save(car: Car): Boolean {
        var isSaved = false
        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, car.id)
                put(COLUMN_NAME_PRICE, car.price)
                put(COLUMN_NAME_BATTERY, car.battery)
                put(COLUMN_NAME_POWER, car.power)
                put(COLUMN_NAME_RECHARGE, car.recharge)
                put(COLUMN_NAME_URL_PHOTO, car.urlPhoto)
            }

            val inserted = db?.insert(CarsContract.CarEntry.TABLE_NAME, null, values)

            if (inserted != null) {
                isSaved = true
            }

        } catch (ex: Exception) {
            ex.message?.let {
                Log.e("Erro ao inserir -> ", it)
            }
        }

        return isSaved
    }

    fun findCarById(id: Int): Car {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase
        //Listam das columas a serem exibidas no resultado da Query
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POWER,
            COLUMN_NAME_RECHARGE,
            COLUMN_NAME_URL_PHOTO
        )

        val filter = "$COLUMN_NAME_CAR_ID = ?"
        val filterValues = arrayOf(id.toString())

        val cursor = db.query(
            CarsContract.CarEntry.TABLE_NAME, // NOME DA TABELA
            columns, //as colunas a serem exibibas
            filter, // where (filtro)
            filterValues, // valor do where, substituindo o parametro ?
            null,
            null,
            null
        )

        var itemId: Long = 0
        var price = ""
        var battery = ""
        var power = ""
        var recharge = ""
        var urlPhoto = ""

        with(cursor) {
            while (moveToNext()) {
                itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                Log.d("ID -> ", itemId.toString())

                price = getString(getColumnIndexOrThrow(COLUMN_NAME_PRICE))
                Log.d("preco -> ", price)

                battery = getString(getColumnIndexOrThrow(COLUMN_NAME_BATTERY))
                Log.d("bateria -> ", battery)

                power = getString(getColumnIndexOrThrow(COLUMN_NAME_POWER))
                Log.d("potencia -> ", power)

                recharge = getString(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE))
                Log.d("recarga -> ", recharge)

                urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.d("urlPhoto -> ", urlPhoto)

            }
        }
        cursor.close()

        return Car(
            id = itemId.toInt(),
            price = price,
            battery = battery,
            power = power,
            recharge = recharge,
            urlPhoto = urlPhoto,
            isFavorite = true
        )

    }

    fun saveIfNotExist(car: Car) {
        val car = findCarById(car.id)
        if (car.id == ID_WHEN_NO_CAR) {
            save(car)
        }
    }

    fun getAll(): List<Car> {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase
        //Listam das columas a serem exibidas no resultado da Query
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POWER,
            COLUMN_NAME_RECHARGE,
            COLUMN_NAME_URL_PHOTO
        )

        val cursor = db.query(
            CarsContract.CarEntry.TABLE_NAME, // NOME DA TABELA
            columns, //as colunas a serem exibibas
            null, // where (filtro)
            null, // valor do where, substituindo o parametro ?
            null,
            null,
            null
        )

        val cars = mutableListOf<Car>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                Log.d("ID -> ", itemId.toString())

                val price = getString(getColumnIndexOrThrow(COLUMN_NAME_PRICE))
                Log.d("preco -> ", price)

                val battery = getString(getColumnIndexOrThrow(COLUMN_NAME_BATTERY))
                Log.d("bateria -> ", battery)

                val power = getString(getColumnIndexOrThrow(COLUMN_NAME_POWER))
                Log.d("potencia -> ", power)

                val recharge = getString(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE))
                Log.d("recarga -> ", recharge)

                val urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.d("urlPhoto -> ", urlPhoto)

                cars.add(
                    Car(
                        id = itemId.toInt(),
                        price = price,
                        battery = battery,
                        power = power,
                        recharge = recharge,
                        urlPhoto = urlPhoto,
                        isFavorite = true
                    )
                )
            }
        }
        cursor.close()
        return cars
    }

    companion object {
        const val ID_WHEN_NO_CAR = 0
    }
}