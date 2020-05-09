package com.mesutdurmuser.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mesutdurmuser.kotlincountries.model.Country

//DAO uygulamadaki bazı işlemlerin farklı threadlar üzerinde çalışmasına yardımcı olan yapıdır. Veritabanından verileri arka planda çek gibi.

@Dao
interface CountryDao {

    //Data access object

    @Insert
    suspend fun insertAll (vararg countries: Country) : List<Long>

    //Insert -> INSERT INTO   sql deki insert into görevi
    //suspend -> corountine, pause & resume durdurup devam ettirmeye olanak veren fonksiyonları
    //vararf -> farklı sayılardaki bir objeyi tek tek veriyoruz
    //List<Long> -> long listesi uuid leri döndürecek veri tabanındaki primary keyleri


    @Query ("SELECT * FROM country") //country isimli veri tabanındakilerin hepsini çek
    suspend fun getAllCountries (): List<Country> //bütün countryleri ver liste şeklinde

    @Query ("SELECT * FROM country WHERE uuid = :countryId") //bu sorguda countryId olanları çek diyoruz
    suspend fun getCountry (countryId: Int) : Country

    @Query ("DELETE FROM country")  //bu da silme işlemimiz
    suspend fun deleteAllCountries()
}