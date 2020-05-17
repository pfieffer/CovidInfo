package com.ravigarbuja.covidinfo.ui.country.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.ravigarbuja.covidinfo.BR
import com.ravigarbuja.covidinfo.INTENT_EXTRA_COUNTRY_DATA
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.base.BaseActivity
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.data.model.DayCase
import com.ravigarbuja.covidinfo.databinding.ActivityCountryDetailBinding
import com.ravigarbuja.covidinfo.util.Resource
import com.ravigarbuja.covidinfo.util.Status
import com.ravigarbuja.covidinfo.util.showToast
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
                countryDetailViewModel.calcMortalityPercent()
                countryDetailViewModel.calcRecoveryPercent()
                countryDetailViewModel.loadAllCasesDataSinceDayOne()
            }
        }

        setupObservable()

    }

    private fun setupObservable() {
        with(countryDetailViewModel) {
            allCasesSinceDayOneLiveData.observe(this@CountryDetailActivity, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        showLoading("")
                    }
                    Status.ERROR -> {
                        hideLoading()
                        showToast("Error")
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        initLineChart(it.data!!)
                    }

                }
            })
        }
    }

    companion object {
        fun getInstance(context: Context, country: Country): Intent {
            return Intent(context, CountryDetailActivity::class.java).apply {
                putExtra(INTENT_EXTRA_COUNTRY_DATA, country)
            }
        }
    }

    private fun initLineChart(listOfDayCase: List<DayCase>) {
        // background color
        lc_main.setBackgroundColor(Color.WHITE)

        // disable description text
        lc_main.description.isEnabled = true
        val desc = Description()
        desc.text = "Since day one"
        lc_main.description = desc

        // enable touch gestures
        lc_main.setTouchEnabled(true)

        // set listeners
        //        lc_main.setOnChartValueSelectedListener(this)
        lc_main.setDrawGridBackground(false)

        // create marker to display box when values are selected
        //        val mv = MyMarkerView(this, R.layout.custom_marker_view)

        // Set the marker to the lc_main
        //        mv.setChartView(lc_main)
        //        lc_main.setMarker(mv)

        // enable scaling and dragging
        lc_main.isDragEnabled = true
        lc_main.setScaleEnabled(true)
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        lc_main.setPinchZoom(true)

        setData(listOfDayCase)

        // draw points over time
        lc_main.animateX(1500)

        // get the legend (only possible after setting data)
        val legend: Legend = lc_main.legend

        // draw legend entries as lines
        legend.form = LegendForm.LINE
    }

    private fun setData(listOfDayCase: List<DayCase>) {
        val noOfXEntries = listOfDayCase.size

        val confirmedValues: ArrayList<Entry> = ArrayList()
        val recoveredValues: ArrayList<Entry> = ArrayList()
        val deathValues: ArrayList<Entry> = ArrayList()

        for (i in 0 until noOfXEntries) {
            val confirmed = listOfDayCase[i].confirmed
            confirmedValues.add(Entry(i.toFloat(), confirmed.toFloat()))

            val recovered = listOfDayCase[i].recovered
            recoveredValues.add(Entry(i.toFloat(), recovered.toFloat()))

            val deaths = listOfDayCase[i].deaths
            deathValues.add(Entry(i.toFloat(), deaths.toFloat()))
        }

        val confirmedLineDataSet: LineDataSet
        val recoveredLineDataSet: LineDataSet
        val deathLineDataSet: LineDataSet

        if (lc_main.data != null &&
            lc_main.data.dataSetCount > 0
        ) {
            confirmedLineDataSet = lc_main.data.getDataSetByIndex(0) as LineDataSet
            recoveredLineDataSet = lc_main.data.getDataSetByIndex(1) as LineDataSet
            deathLineDataSet = lc_main.data.getDataSetByIndex(2) as LineDataSet

            confirmedLineDataSet.values = confirmedValues
            recoveredLineDataSet.values = recoveredValues
            deathLineDataSet.values = deathValues

            confirmedLineDataSet.notifyDataSetChanged()
            recoveredLineDataSet.notifyDataSetChanged()
            deathLineDataSet.notifyDataSetChanged()

            lc_main.data.notifyDataChanged()
            lc_main.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            confirmedLineDataSet = LineDataSet(confirmedValues, "Cases confirmed")
            recoveredLineDataSet = LineDataSet(recoveredValues, "Recovered cases")
            deathLineDataSet = LineDataSet(deathValues, "Deaths")

            confirmedLineDataSet.setDrawIcons(false)
            recoveredLineDataSet.setDrawIcons(false)
            deathLineDataSet.setDrawIcons(false)

            // draw dashed line
            confirmedLineDataSet.enableDashedLine(10f, 5f, 0f)
            recoveredLineDataSet.enableDashedLine(10f, 5f, 0f)
            deathLineDataSet.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            confirmedLineDataSet.color = Color.BLACK
            recoveredLineDataSet.color = Color.GREEN
            deathLineDataSet.color = Color.RED

            confirmedLineDataSet.setCircleColor(Color.BLACK)
            recoveredLineDataSet.setCircleColor(Color.GREEN)
            deathLineDataSet.setCircleColor(Color.RED)

            // line thickness and point size
            confirmedLineDataSet.lineWidth = 1f
            recoveredLineDataSet.lineWidth = 1f
            deathLineDataSet.lineWidth = 1f
//            confirmedLineDataSet.circleRadius = 2f
//            recoveredLineDataSet.circleRadius = 2f
//            deathLineDataSet.circleRadius = 2f

            // draw points as solid circles
            confirmedLineDataSet.setDrawCircleHole(false)
            recoveredLineDataSet.setDrawCircleHole(false)
            deathLineDataSet.setDrawCircleHole(false)

            // text size of values
            confirmedLineDataSet.valueTextSize = 9f


            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            // add the data sets
            dataSets.add(deathLineDataSet)
            dataSets.add(recoveredLineDataSet)
            dataSets.add(confirmedLineDataSet)

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            lc_main.data = data
        }
    }
}
