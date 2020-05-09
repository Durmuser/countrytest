package com.mesutdurmuser.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
//import com.mesutdurmuser.kotlincountries.CountryFragmentArgs
import com.mesutdurmuser.kotlincountries.R
import com.mesutdurmuser.kotlincountries.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*

class CountryFragment : Fragment() {

    private lateinit var viewModel : CountryViewModel //countryview modelden bir değişken oluşturuyoruz

    private var countryUuid = 0 //


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)  //viewmodel imizı çağırıyoruz ve izlemeye alıyoruz.
        viewModel.getDataFromRoom()  //ViewModel de ki verileri çağırıyoruz

        arguments?.let {

            countryUuid = CountryFragmentArgs.fromBundle(
                it
            ).countryUuid
        }
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->  //countryLiveData yı izlğiyoruz
            country?.let { //eğer boş değilse aşağıyı çalıştır
                countryName.text = country.countryName   //burada countryName gibi verileri çağırıyoruz. ve textlere gösteriyoruz.
                countryCapital.text = country.countryCapital
                countryCurrency.text = country.countryCurrency
                countryRegion.text = country.countryRegion
                countryLanguage.text = country.countryLanguage


            }
        })
    }

}
