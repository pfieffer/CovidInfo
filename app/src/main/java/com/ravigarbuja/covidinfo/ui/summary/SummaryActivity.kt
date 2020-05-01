package com.ravigarbuja.covidinfo.ui.summary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ravigarbuja.covidinfo.BR
import com.ravigarbuja.covidinfo.INTENT_EXTRA_DATE_DATA
import com.ravigarbuja.covidinfo.INTENT_EXTRA_GLOBAL_DATA
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.base.BaseActivity
import com.ravigarbuja.covidinfo.data.network.model.Global
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
        setUpToolbarWithBackButton()

        intent.extras?.getParcelable<Global>(INTENT_EXTRA_GLOBAL_DATA)?.let{
            summaryViewModel.setGlobalData(it)
        }
        intent.extras?.getString(INTENT_EXTRA_DATE_DATA)?.let {
            summaryViewModel.setDate(it)
        }
    }

    companion object {
        fun getInstance(context: Context, globalSummaryData: Global, dateOfData: String): Intent {
            return Intent(context, SummaryActivity::class.java).apply {
                putExtra(INTENT_EXTRA_GLOBAL_DATA, globalSummaryData)
                putExtra(INTENT_EXTRA_DATE_DATA, dateOfData)
            }
        }
    }
}
