package com.mesutdurmuser.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mesutdurmuser.kotlincountries.R
import com.mesutdurmuser.kotlincountries.adapter.CountryAdapter
import com.mesutdurmuser.kotlincountries.view.CountryFragmentArgs
import com.mesutdurmuser.kotlincountries.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*



class FeedFragment : Fragment() {

    private lateinit var viewModel : FeedViewModel  //oluşturudumuz adapter ve view model için değişkenler oluşturuyoruz.
    private val countryAdapter = CountryAdapter(arrayListOf()) //adapter i tanımlıyoruz arraylistof şimidlik boş bırakmak için verildi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java) //providers hangi aktivitedeyiz, fragmentteyiz ve hangi view model sınıfıyla çalışmak istediğimizi söylüyoruz.
        viewModel.refreshData()

        countryList.layoutManager = LinearLayoutManager(context) //country list i çağırdık linear manager ile eşledik çünkü alt alta olsun diye item de.
        countryList.adapter = countryAdapter   //bu listenin adapterini belirtiyoruz


        swipeRefreshLayout.setOnRefreshListener {  //swipeRefreshLayout çağırdık aşağıya çektiğimizde ne olacağını söylüyoruz.
            countryList.visibility = View.GONE  //country list gösterme
            countryError.visibility = View.GONE  //country error gösterme
            countryLoading.visibility = View.VISIBLE  //countryLoading göster
            viewModel.refreshData()  //viewmodel den verileri yeniden çek dedik
            swipeRefreshLayout.isRefreshing = false //burada yukarıdaki refresh kapattık ki bizimki görünsün ortada olan

        }

        observerLiveData()  //aşağıda yazdığımız fonksiyonu burada çağırıyoruz oncreatedView ın altında. gösterilecek olanlar burada çağırılıyordu fragmentlerde

   /*     fragment_btn.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }*/
    }

    private fun observerLiveData(){  //observe gözlemlemek demek live datadaları burada gözlemliyoruz
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->  //gözlemlediğimiz countries i belirtiyoruz. viewLifecycleOwner yaşam döngüsü
            countries?.let{        //burada countries boş mu geliyor onu kontrol ediyoruz
                countryList.visibility = View.VISIBLE  //eğer countries geldiyse gösteriyoruz. error ve loading i gizlemiş oluyoruz. countryList recyclerView imiz
                countryAdapter.updateCountryList(countries)  //adapterimizi çağırıyoruz countries içinde görünsün diye.
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->  //burada error u izliyoruz. nerede görünüp görünmeyeceğini söylüyoruz. bu satırda error değişkenini veriyoruz.
            error?.let {
                if(it){  //burada if it kullandık. boolean true ise diye diyoruz.
                    countryError.visibility = View.VISIBLE   //eğer hata mesajı varsa bunu göster
                }else{
                    countryError.visibility = View.GONE  //gone gösterme! demek
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it) {
                    countryLoading.visibility = View.VISIBLE  //eğer loading yükleniyorsa. diğerlerini aşağıda gizliyoruz.
                    countryList.visibility = View.GONE
                    countryError.visibility = View.GONE
                }else{
                    countryLoading.visibility = View.GONE
                }
            }
        })
    }



}

