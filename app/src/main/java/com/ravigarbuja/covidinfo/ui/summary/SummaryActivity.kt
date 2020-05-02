package com.ravigarbuja.covidinfo.ui.summary

import android.content.Context
import android.content.Intent
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import com.ravigarbuja.covidinfo.BR
import com.ravigarbuja.covidinfo.INTENT_EXTRA_DATE_DATA
import com.ravigarbuja.covidinfo.INTENT_EXTRA_GLOBAL_DATA
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.base.BaseActivity
import com.ravigarbuja.covidinfo.data.model.Global
import com.ravigarbuja.covidinfo.databinding.ActivitySummaryBinding
import kotlinx.android.synthetic.main.activity_summary.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SummaryActivity : BaseActivity<SummaryViewModel, ActivitySummaryBinding>(), OnChartValueSelectedListener {

    private val summaryViewModel: SummaryViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_summary

    override fun getViewModel(): SummaryViewModel = summaryViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    private lateinit var mBinding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = mViewDataBinding
        setUpToolbarWithBackButton()

        intent.extras?.getParcelable<Global>(INTENT_EXTRA_GLOBAL_DATA)?.let {
            summaryViewModel.setGlobalData(it)
            setDataForTotalInstances(it)
            setDataForNewInstances(it)
        }
        intent.extras?.getString(INTENT_EXTRA_DATE_DATA)?.let {
            summaryViewModel.setDateTime(it)
        }

        bar_chart.setOnChartValueSelectedListener(this)
        bar_chart.setDrawBarShadow(false)
        bar_chart.setDrawValueAboveBar(false)
        bar_chart.description.isEnabled = true
        // if more than 60 entries are displayed in the bar_chart, no values will be
        // drawn
        bar_chart.setMaxVisibleValueCount(60)
        // scaling can now only be done on x- and y-axis separately
        bar_chart.setPinchZoom(false)
        bar_chart.setDrawGridBackground(false)

        bar_chart_new_instances.setOnChartValueSelectedListener(this)
        bar_chart_new_instances.setDrawBarShadow(false)
        bar_chart_new_instances.setDrawValueAboveBar(false)
        bar_chart_new_instances.description.isEnabled = true
        // if more than 60 entries are displayed in the bar_chart_new_instances, no values will be
        // drawn
        bar_chart_new_instances.setMaxVisibleValueCount(60)
        // scaling can now only be done on x- and y-axis separately
        bar_chart_new_instances.setPinchZoom(false)
        bar_chart_new_instances.setDrawGridBackground(false)
    }

    private fun setDataForNewInstances(globalSummaryData: Global) {
        val values: ArrayList<BarEntry> = ArrayList()
        values.add(BarEntry(1F, globalSummaryData.newConfirmed.toFloat()))
        values.add(BarEntry(2F, globalSummaryData.newRecovered.toFloat()))
        values.add(BarEntry(3F, globalSummaryData.newDeaths.toFloat()))

        val set1: BarDataSet

        if (bar_chart_new_instances.data != null &&
            bar_chart_new_instances.data.dataSetCount > 0
        ) {
            set1 = bar_chart_new_instances.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            bar_chart_new_instances.data.notifyDataChanged()
            bar_chart_new_instances.notifyDataSetChanged()
        } else{
            set1 = BarDataSet(values, "New instances of Corona virus")

            set1.setDrawIcons(true)

            val dataSets: ArrayList<IBarDataSet> = ArrayList()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.barWidth = 0.9f

            bar_chart_new_instances.data = data
        }

    }

    private fun setDataForTotalInstances(globalSummaryData: Global){
        val values: ArrayList<BarEntry> = ArrayList()
        values.add(BarEntry(1F, globalSummaryData.totalConfirmed.toFloat()))
        values.add(BarEntry(2F, globalSummaryData.totalRecovered.toFloat()))
        values.add(BarEntry(3F, globalSummaryData.totalDeaths.toFloat()))

        val set1: BarDataSet

        if (bar_chart.data != null &&
            bar_chart.data.dataSetCount > 0
        ) {
            set1 = bar_chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            bar_chart.data.notifyDataChanged()
            bar_chart.notifyDataSetChanged()
        } else{
            set1 = BarDataSet(values, "Total cases")

            set1.setDrawIcons(true)

            val dataSets: ArrayList<IBarDataSet> = ArrayList()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.barWidth = 0.9f

            bar_chart.data = data
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

    override fun onNothingSelected() {
        Log.d("On: ", "Nothing selected")
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null) return

        val bounds: RectF = onValueSelectedRectF
        bar_chart.getBarBounds(e as BarEntry?, bounds)
        val position: MPPointF = bar_chart.getPosition(e, AxisDependency.LEFT)

        Log.i("bounds", bounds.toString())
        Log.i("position", position.toString())

        Log.i(
            "x-index",
            "low: " + bar_chart.getLowestVisibleX().toString() + ", high: "
                    + bar_chart.getHighestVisibleX()
        )

        MPPointF.recycleInstance(position)
    }

    private val onValueSelectedRectF = RectF()
}
