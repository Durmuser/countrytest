package com.mesutdurmuser.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {


    private val job = Job() // Coroutine den job isminde bir nesne oluşturuyoruz bu iş yap demek
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() { //burada her hangi bir hata durumunda job u bırak demek
        super.onCleared()
        job.cancel()
    }

}