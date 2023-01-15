package Data

import Model.Car
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import Utils.*
import android.content.ContentValues


@RequiresApi(Build.VERSION_CODES.P)
class DataBaseHandler(
    context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int
) : SQLiteOpenHelper(context, Utils.TABLE_NAME, factory, Utils.DATABASE_VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_CARS_TABLE = "CREATE TABLE " + Utils.TABLE_NAME +
                "("+Utils.KEY_ID+ " INTEGER PRIMARY KEY," + Utils.KEY_NAME +
                " TEXT," + Utils.KEY_PRICE + " TEXT)"

        p0?.execSQL(CREATE_CARS_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS " + Utils.DATABASE_NAME)
        onCreate(p0)
    }

    fun addCar(car: Car){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Utils.KEY_NAME, car.name)
        contentValues.put(Utils.KEY_PRICE, car.price)
        db.insert(Utils.TABLE_NAME, null, contentValues)
        db.close()
    }

    fun getCar(id: Int) : Car {
        val db = this.readableDatabase
        val cursor = db.query(Utils.TABLE_NAME, arrayOf<String>(Utils.KEY_ID, Utils.KEY_NAME, Utils.KEY_PRICE), Utils.KEY_ID + "=?",
            arrayOf(java.lang.String.valueOf(id)), null, null, null, null)
        cursor?.moveToFirst()
        val car = Car(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2))
        db.close()
        return car
    }

    fun getAllCars() : List<Car>{
        val db = this.readableDatabase
        val listOfCars = ArrayList<Car>()
        val selectAllCars = "SELECT * FROM " + Utils.TABLE_NAME
        val cursor = db.rawQuery(selectAllCars,null)
        if (cursor.moveToFirst())
        {
            do {
                val car = Car()
                car.id = Integer.parseInt(cursor.getString(0))
                car.name = cursor.getString(1)
                car.price = cursor.getString(2)
                listOfCars.add(car)
            } while (cursor.moveToNext())
        }
        return listOfCars
    }

    fun updateCar(car: Car) : Int
    {
        var db = this.readableDatabase

        var contentValues = ContentValues()
        contentValues.put(Utils.KEY_NAME, car.name)
        contentValues.put(Utils.KEY_PRICE, car.price)

        return db.update(Utils.TABLE_NAME, contentValues, Utils.KEY_ID + "=?", arrayOf(car.id.toString()))
    }

    fun deleteCar(car: Car)
    {
        val db = this.readableDatabase
        db.delete(Utils.TABLE_NAME, Utils.KEY_ID + "=?", arrayOf(car.id.toString()))
    }

    fun getCarCount() : Int
    {
        val db = this.readableDatabase

        val countQuery = "SELECT * FROM " + Utils.TABLE_NAME

        val cursor = db.rawQuery(countQuery, null)

        return cursor.count
    }

    //CRUD
}