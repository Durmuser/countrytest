package com.mesutdurmuser.kotlincountries.service

import com.mesutdurmuser.kotlincountries.model.Country
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST

//burada retrofitte ne işlem yapacağımızı söylüyoruz. get post vb. API nelere izin veriyorsa hepsi yapılabilir.

//RxJavayı da burada kullanıyoruz sitesinden bak. Observable, Flowable, Single,

interface CountryAPI {

    //GET, POST

    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    //BASE_URL https://raw.githubusercontent.com/ bizim normal adresimiz
    //EXT atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json   normal adresin devamı

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>  //buradaki fonksiyonda verilerin Single olarak alınacağını söyledik RxJava nın sınıfıdır. liste olarak geleceğini ve Countryler geleceğini söylüyoruz.





    //single demek bir defa ve garantili olarak al demek. biz bir kere alacağımız için bunu kullandık. observable kullanılabilirdi yaygın olarak o kullanılır.
    //observable aralıklarla al anlamında ama hata payı olabilir.
}