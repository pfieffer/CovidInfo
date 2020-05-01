package com.ravigarbuja.covidinfo.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.ravigarbuja.covidinfo.BR
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.base.BaseActivity
import com.ravigarbuja.covidinfo.data.network.model.Country
import com.ravigarbuja.covidinfo.data.network.model.Global
import com.ravigarbuja.covidinfo.databinding.ActivityMainBinding
import com.ravigarbuja.covidinfo.ui.countries.CountryListActivity
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
        mBinding = mViewDataBinding
        mainViewModel.setNavigator(this)
        initObservable()

    }

    private fun initObservable() {
        with(mainViewModel) {
            summaryLiveData.observe(this@MainActivity, Observer {
                when (it.status) {
                    Status.PROGRESS -> {
                        hideLoading()
                    }
                    Status.LOADING -> {
                        showLoading("")
                    }
                    Status.ERROR -> {
                        hideLoading()
                        showToast("Error")
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        this.populateData(it.data!!)
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
    /**
     * End: MainNavigator Implementations
     */
}
