package com.ravigarbuja.covidinfo.ui.countries

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.ravigarbuja.covidinfo.BR
import com.ravigarbuja.covidinfo.INTENT_EXTRA_COUNTRIES_DATA
import com.ravigarbuja.covidinfo.R
import com.ravigarbuja.covidinfo.base.BaseActivity
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.databinding.ActivityCountryListBinding
import com.ravigarbuja.covidinfo.util.showToast
import kotlinx.android.synthetic.main.activity_country_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CountryListActivity : BaseActivity<CountryListViewModel, ActivityCountryListBinding>(),
    CountryListNavigator {

    private val countryListViewModel: CountryListViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_country_list

    override fun getViewModel(): CountryListViewModel = countryListViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    private lateinit var mBinding: ActivityCountryListBinding

    private lateinit var countryListAdapter: CountryListAdapter

    private var query = ""
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = mViewDataBinding
        setUpToolbarWithBackButton()
        countryListViewModel.setNavigator(this)

        val data: ArrayList<Country>
        if (intent.hasExtra(INTENT_EXTRA_COUNTRIES_DATA)) {
            data = intent.getParcelableArrayListExtra<Country>(
                INTENT_EXTRA_COUNTRIES_DATA
            )!!
            countryListViewModel.countryList.postValue(data)
            setUpRecyclerView(data)
        }


    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_country, menu)

        menu?.findItem(R.id.action_search)?.run {
            searchView = actionView as SearchView

            val searchEdit: EditText =
                searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
            searchEdit.hint = resources.getString(R.string.menu_search_et_hint)
            val view = searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)
            view.layoutParams.height = 105
            view.setPadding(0, 0, 10, 0)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String?): Boolean {
                    queryText?.let {
                        countryListViewModel.launchWithDebounce {
                            if (mBinding.rvCountriesList.scrollState == RecyclerView.SCROLL_STATE_IDLE) //mViewDataBinding
                                countryListAdapter.filter.filter(it.trim())
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        countryListViewModel.launchWithDebounce {
                            if (mViewDataBinding.rvCountriesList.scrollState == RecyclerView.SCROLL_STATE_IDLE)//mViewDataBinding
                                countryListAdapter.filter.filter(it.trim())
                        }
                    }
                    return true
                }
            })
        }
        return true
    }


    private fun setUpRecyclerView(data: MutableList<Country>) {
        countryListAdapter = CountryListAdapter(
            countryListViewModel = countryListViewModel
        )
        countryListAdapter.setCountryList(data)

        mBinding.rvCountriesList.adapter = countryListAdapter

        with(rv_countries_list) {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (query.isNotEmpty())
                            countryListAdapter.filter.filter(query.trim())
                    }
                }
            })
        }
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
