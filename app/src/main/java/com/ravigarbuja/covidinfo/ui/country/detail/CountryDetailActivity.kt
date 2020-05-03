package com.ravigarbuja.covidinfo.ui.country.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ravigarbuja.covidinfo.BR
import com.ravigarbuja.covidinfo.INTENT_EXTRA_COUNTRY_DATA
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.base.BaseActivity
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.databinding.ActivityCountryDetailBinding
import kotlinx.android.synthetic.main.activity_country_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountryDetailActivity : BaseActivity<CountryDetailViewModel, ActivityCountryDetailBinding>() {

    private val countryDetailViewModel: CountryDetailViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_country_detail

    override fun getViewModel(): CountryDetailViewModel = countryDetailViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    private lateinit var mBinding: ActivityCountryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = mViewDataBinding

        setUpToolbarWithBackButton(toolbar_country_detail)

        intent.getParcelableExtra<Country>(INTENT_EXTRA_COUNTRY_DATA).let {
            if (it != null) {
                countryDetailViewModel.currentCountry.set(it)
            }
        }


    }

    companion object {
        fun getInstance(context: Context, country: Country): Intent {
            return Intent(context, CountryDetailActivity::class.java).apply {
                putExtra(INTENT_EXTRA_COUNTRY_DATA, country)
            }
        }
    }
}
