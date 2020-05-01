package com.ravigarbuja.covidinfo.ui.countries

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.ravigarbuja.covidinfo.BR
import com.ravigarbuja.covidinfo.INTENT_EXTRA_COUNTRIES_DATA
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.base.BaseActivity
import com.ravigarbuja.covidinfo.data.network.model.Country
import com.ravigarbuja.covidinfo.databinding.ActivityCountryListBinding
import com.ravigarbuja.covidinfo.util.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class CountryListActivity : BaseActivity<CountryListViewModel, ActivityCountryListBinding>(),
    CountryListNavigator {

    private val countryListViewModel: CountryListViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_country_list

    override fun getViewModel(): CountryListViewModel = countryListViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    private lateinit var mBinding: ActivityCountryListBinding

    private lateinit var countryListAdapter: CountryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = mViewDataBinding
        countryListViewModel.setNavigator(this)

        val data: ArrayList<Country>
        if (intent.hasExtra(INTENT_EXTRA_COUNTRIES_DATA)) {
            data = intent.getParcelableArrayListExtra<Country>(
                INTENT_EXTRA_COUNTRIES_DATA
            )!!
            for (country in data) {
                Log.d("Country: ", country.name)
            }
            setUpRecyclerView(data)
        }


    }


    private fun setUpRecyclerView(data: ArrayList<Country>) {
        countryListAdapter = CountryListAdapter(
            data, this
        )
        mBinding.rvCountriesList.adapter = countryListAdapter
    }


    companion object {
        fun getInstance(context: Context, countriesData: ArrayList<Country>): Intent {
            return Intent(context, CountryListActivity::class.java).apply {
                putParcelableArrayListExtra(INTENT_EXTRA_COUNTRIES_DATA, countriesData)
            }
        }
    }

    override fun onItemClick(country: Country) {
        showToast("Open detail view for ${country.name}")
    }
}
