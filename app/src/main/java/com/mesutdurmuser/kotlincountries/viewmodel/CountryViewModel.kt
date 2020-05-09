package com.mesutdurmuser.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mesutdurmuser.kotlincountries.model.Country


//burası detayları gösterdiğimiz ViewModel imiz.
class CountryViewModel : ViewModel() {

    val countryLiveData = MutableLiveData<Country>()  //countrylivedata değişkeni oluşturuyoruz mutableLiveData olarak. Countryden çoğaltıyoruz.


    fun getDataFromRoom(){  //burada internetten çektiğimiz verileri işliyoruz.
        val country = Country ("Turkey","Asia","Ankara", "TRY", "Turkish", "wwww.ss.com")
        countryLiveData.value = country  //countryLivedata ya değer olarak country i atıyoruz liste olan country
    }

}