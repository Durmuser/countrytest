package com.mesutdurmuser.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mesutdurmuser.kotlincountries.R
import com.mesutdurmuser.kotlincountries.model.Country
import com.mesutdurmuser.kotlincountries.util.downloadFromUrl
import com.mesutdurmuser.kotlincountries.util.placholderProgressBar
import com.mesutdurmuser.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter (val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHoldder>() {  //Recycler view den implement





    class CountryViewHoldder(var view: View) : RecyclerView.ViewHolder(view) {   //implement oluştururken istiyor sınıfın içinde oluşturmaya dikkat et

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHoldder {  //layout ile adapter i bağladığımız yer

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country,parent,false)
        return CountryViewHoldder(view)

    }

    override fun getItemCount(): Int {   //listedeki eleman sayısı kadar item oluşturduğumuz yer o yüzden listenin boyutu verilir

        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHoldder, position: Int) {   //yukarıda birbirlerine bağladığımız için burada içeriğe ulaşabiliyoruz holder ile. position larını alıyoruz

        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion

        holder.view.setOnClickListener {                               //burada bir view a yani recyclerdaki item e tıklandığında ne olacağını belirtiyoruz
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()  //action isminde bir değişken oluşturuyoruz.akisoynun nereden geleceği FeedFragmentDirections
            Navigation.findNavController(it).navigate(action) // nav controllere action u atıyoruz
        }
        // Utilde oluşturduğumuz yer burası
        holder.view.imageView.downloadFromUrl(countryList[position].imageUrl, placholderProgressBar(holder.view.context)) //burada da hangi resmin nerede görüneceğini söylüyoruz.


    }


    //burada swipe refresh tanımlarız.. veriler güncellendiğinde adapter e bildirmemiz gerekir o yüzden
    fun updateCountryList (newCountryList: List<Country>){
        countryList.clear() //listeyi boşaltıyoruz
        countryList.addAll(newCountryList) //yeni listeyi tekrar istiyoruz
        notifyDataSetChanged() //adapter ı yenilemek
    }
}