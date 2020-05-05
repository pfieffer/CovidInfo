package com.ravigarbuja.covidinfo.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.ravigarbuja.covidinfo.BR
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.base.BaseActivity
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.data.model.Global
import com.ravigarbuja.covidinfo.databinding.ActivityMainBinding
import com.ravigarbuja.covidinfo.ui.country.detail.CountryDetailActivity
import com.ravigarbuja.covidinfo.ui.country.list.CountryListActivity
import com.ravigarbuja.covidinfo.ui.summary.SummaryActivity
import com.ravigarbuja.covidinfo.util.Status
import com.ravigarbuja.covidinfo.util.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), MainNavigator {

    private val mainViewModel: MainViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): MainViewModel = mainViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservable()
        mBinding = mViewDataBinding
        mainViewModel.setNavigator(this)

    }

    private fun initObservable() {
        with(mainViewModel) {
            summaryLiveData.observe(this@MainActivity, Observer { responseResource ->
                when (responseResource.status) {
                    Status.PROGRESS -> {
                        showLoading("")
                    }
                    Status.LOADING -> {
                        showLoading("")
                    }
                    Status.ERROR -> {
                        hideLoading()
                        this.dataLoaded.postValue(false)
                        showToast("Error")
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        this.populateData(responseResource.data!!)
                    }
                }
            })
        }
    }

    /**
     * Start: MainNavigator Implementations
     */
    override fun navigateToSummaryDetail(global: Global, date: String) {
        startActivity(SummaryActivity.getInstance(this, global, date))
    }

    override fun navigateToByCountriesScreen(countryList: ArrayList<Country>) {
        startActivity(CountryListActivity.getInstance(this, countryList))
    }

    override fun navigateToMyCountryDetail(country: Country) {
        startActivity(CountryDetailActivity.getInstance(this, country))
    }
    /**
     * End: MainNavigator Implementations
     */
}
