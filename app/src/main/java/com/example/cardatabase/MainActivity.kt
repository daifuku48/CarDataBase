package com.example.cardatabase

import Data.DataBaseHandler
import Model.Car
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import Utils.*
import android.util.Log

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataBaseHandler = DataBaseHandler(this, Utils.TABLE_NAME, null, Utils.DATABASE_VERSION)
        dataBaseHandler.addCar(Car("Toyota", "2324"))
        dataBaseHandler.addCar(Car("Aboba", "32"))

        val list = dataBaseHandler.getAllCars()
        dataBaseHandler.deleteCar(Car("Aboba", "32"))
        for (i in list)
        {
            Log.d("message", "${i.id} ${i.name} ${i.price}")
        }
        val car = dataBaseHandler.getCar(2)
        Log.d("mess1", "${car.id} ${car.name} ${car.price}")

        dataBaseHandler.updateCar(car)
    }
}