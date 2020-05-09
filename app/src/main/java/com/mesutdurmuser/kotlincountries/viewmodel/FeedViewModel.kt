package com.mesutdurmuser.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mesutdurmuser.kotlincountries.model.Country
import com.mesutdurmuser.kotlincountries.service.CountryAPIService
import com.mesutdurmuser.kotlincountries.service.CountryDatabase
import com.mesutdurmuser.kotlincountries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel (application: Application) : BaseViewModel(application) {  //viewmodel den implemen ediyoruz

    private val countryAPIService = CountryAPIService()  //servisten bir obje oluşturudk
    private val disposable = CompositeDisposable() //internetten birşey indirirkenb her kol bir olşuştururuz ve bunlar yer kaplar.
    // bir tane büyük bir obje oluşturur internetten inen verileri buraya depoluyoruz ileride bunu sildiğimizde verilerden kurtulmuş oluruz.

    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 *1000L

    val countries = MutableLiveData<List<Country>>()    //burada error yükleniyor ve ülkelerin değişkenleri oluşturuluyor mutable live data değiştirilebilir demek
    val countryError = MutableLiveData<Boolean>()  //hata ile ilgili değişken
    val countryLoading = MutableLiveData<Boolean>() //ülkeler yükleniyor

    fun refreshData(){  // yukarıdaki mutablelive datalara veri ekliyor
        val updateTime = customPreferences.getTime()

        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()
        }
    }

    private fun getDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries (countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI(){
        countryLoading.value = true   //kullanıcı önce progress barı görüyor
        disposable.add(  //sonra disposable değişkenine ekliyoruz
            countryAPIService.getData()  //API servisimizdeki verileri çağırıyoruz
                .subscribeOn(Schedulers.newThread())  //asenkronize bir şekilde verileri getiriyoruz.bunu arka bir thread de yapıyoruz
                .observeOn(AndroidSchedulers.mainThread())  // ana thredi izliyoruyz burda
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){  //bir tane object oluşturuyoruz.
                    override fun onSuccess(t: List<Country>) {  //single kullandığımız için geldi error ve positive ne olacağını yazıyoruz. veriler gelirken yapılan burası
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()

                        //manifestten internet izni almayı unutmayalım

                    }

                })
        )
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray()) //individual diziden tek tek
            var i = 0
            while (i < list.size){
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }

            showCountries(list)
        }


        customPreferences.saveTime(System.nanoTime())
    }



}