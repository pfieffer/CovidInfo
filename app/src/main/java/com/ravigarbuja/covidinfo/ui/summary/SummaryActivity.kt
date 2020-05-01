package com.ravigarbuja.covidinfo.ui.summary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ravigarbuja.covidinfo.BR
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.base.BaseActivity
import com.ravigarbuja.covidinfo.databinding.ActivitySummaryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryActivity : BaseActivity<SummaryViewModel, ActivitySummaryBinding>() {

    private val summaryViewModel: SummaryViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_summary

    override fun getViewModel(): SummaryViewModel = summaryViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    private lateinit var mBinding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = mViewDataBinding
    }

    companion object{
        fun start(activity: Activity) {
            with(activity) {
                startActivityForResult(Intent().apply {
                    setClass(this@with, CountryListActivity::class.java)
                }, REQUEST_CODE_SELECT_COUNTRY)
            }
        }
    }
}
