package com.mesutdurmuser.kotlincountries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*Kotlin dosyası oluşturulur. Room rx java palette retrofit hepsi model içinde bulunur.
Sınıf oluşturulur data class olarak.
Data class verilerin tutulduğu sınıflar veritabanı vb veri çekerken parametreleri aşağıdaki gibi yazar, modelleriz.*/



@Entity  //Room için gerekli olan entity sınıfına dönüştürüyoruz
data class Country(
    @ColumnInfo (name = "name") //colon bilgileri
    @SerializedName("name")  //@serialized diyerek veritabanında başka isimler bulunuyorsa bununla countryName gelecek olan name ismine eşittir diyoruz GSON formatında geliyor.
                                            // GSON formatını nasıl işleyeceğimizi söyledik
    val countryName: String?,

    @ColumnInfo (name = "region")
    @SerializedName("region")
    val countryRegion: String?,

    @ColumnInfo (name = "capital")
    @SerializedName("capital")
    val countryCapital: String?,

    @ColumnInfo (name = "currency")
    @SerializedName("currency")
    val countryCurrency: String?,

    @ColumnInfo (name = "language")
    @SerializedName("language")
    val countryLanguage: String?,

    @ColumnInfo (name = "flag")
    @SerializedName("flag")
    val imageUrl: String?){

    @PrimaryKey(autoGenerate = true) //primary key veriyoruz otomatik ata diyoruz
    var uuid: Int = 0
}