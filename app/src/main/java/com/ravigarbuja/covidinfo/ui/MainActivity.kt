package com.ravigarbuja.covidinfo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.databinding.ActivityMainBinding
import com.ravigarbuja.covidinfo.util.Status
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        initObservable()

    }

    private fun initObservable() {
        with(mainViewModel) {
            summaryLiveData.observe(this@MainActivity, Observer {
                when (it.status) {
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                    }
                    Status.SUCCESS -> {
                        tv_main.text = it.data.toString()
                    }
                }
            })
        }
    }
}
