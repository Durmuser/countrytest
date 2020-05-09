package com.mesutdurmuser.kotlincountries.service

import com.mesutdurmuser.kotlincountries.model.Country
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPIService {   //bu sınıfta country API servisimizi yazıyoruz

    //BASE_URL https://raw.githubusercontent.com/
    //EXT atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    private val BASE_URL = "https://raw.githubusercontent.com/"  //basse URL burada kullandık
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)  //api isminde bnir değişken oluşturuyoruz retrofit kullanarak sonunada url mizi verdik.
        .addConverterFactory(GsonConverterFactory.create()) //burada Hson kullanacağımızı söyledik ve çevirdik Gsonconverter factory ile
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //Rxjava kullanacağımızı söylüyoruz ve çevireceğimizi
        .build() //burada üret dedik
        .create(CountryAPI::class.java) //oluşturk dedik ve CountryAPI ile bağladık


    fun getData() : Single<List<Country>>{  //bu fonksiyonda Single döndürecek liste içinde country obşjleri diyoruz
        return api.getCountries()  //döndür dedik api.get Countrires
    }
}