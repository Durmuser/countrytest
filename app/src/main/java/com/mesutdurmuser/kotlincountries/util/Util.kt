package com.mesutdurmuser.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mesutdurmuser.kotlincountries.R

//util demek utility yani bir çok yerde kullanılabilen joker gibi.

//Extension
//glide görselleri çekmek için kullanılıyor picasso gibi




fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable){  //glide için bir fonksiyon oluşturuyoruz böylece isteidğimiz imageView içinde çağıracağız
    val options = RequestOptions()
        .placeholder(progressDrawable)  //görüntüler yüklenene kadar ne görünecek diyor progress bar ı göster diyoruz
        .error(R.mipmap.ic_launcher)  //burada error durumunda projede kullandığımızı göstereceğiz

    Glide.with(context)  //glade ile diyoruz
        .setDefaultRequestOptions(options)
        .load(url)  //url yi yükle
        .into(this)

}

fun placholderProgressBar(context: Context) : CircularProgressDrawable {  //place holder yüklenene (görüntüler) kadar kullanılacak progressbar ı gösteriyoruz
    return CircularProgressDrawable(context).apply {  //CircularProgressDrawable döndür diyoruz aşağıda da özelliklerini gösteriyoruz
        strokeWidth = 8f  //ne kadar genişlikte olacağını belirle
        centerRadius = 40f  //yarı çapı ne kadar olacak onu belirle
        start()
    }
}