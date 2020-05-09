package com.mesutdurmuser.kotlincountries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mesutdurmuser.kotlincountries.model.Country


@Database(entities = arrayOf(Country::class),version = 1)  //database olarak oluşturuyoruz. entities olarak array istiyor. country modelimizi veriyoruz.
// version 1 olarak başlattık ileride güncelleme yaparsak yükseltiyoruz

abstract class CountryDatabase : RoomDatabase() { //sınıfımızı abstract oluşturuyoruz

    abstract fun countryDao() : CountryDao //abstract bir fonksiyon oluşturuyoruz bize dao interface imizi döndürüyor.

    //veri tabanımızdan birden fazla obje oluşturulmasını istemiyoruz çünkü aynı anda birden fazla therad dan ulaşmaya çalışılırsa patlar

    //Singleton yöntemi ile oluşturuyoruz çünkü tek bir obje oluşturuabilen bir yöntem içinde obje yoksa oluşturuyor varsa tekrar oluşturmuyor

    companion object {  // companion object her yerden ulaşabilmemize imkan tanıyor

       @Volatile private var  instance : CountryDatabase? = null //bir database objesi oluşturuyoruz boş olarak Volatile de bu objeyi farklı threadlarda çağrılabileceği için yazıyoruz

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {  //instance var mı yok mu kontrol ediyor varsa döndürüyor. synchronized tek bir threadden ulaşsın demek
            instance ?: makeDatabase(context).also{
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(  //burda roomdan faydalanarak database oluşturacağımız fonksiyonu yazdık
            context.applicationContext,CountryDatabase::class.java,"countrydatabase"  //application context kullandık çünkü herhangi birdurumda yan çevirme teli gibi veritabanı bağlantısı gitmesin istiyoruz
        ).build()

    }



}