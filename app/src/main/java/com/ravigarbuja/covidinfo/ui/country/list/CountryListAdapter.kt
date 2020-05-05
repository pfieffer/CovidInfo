package com.ravigarbuja.covidinfo.ui.country.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ravigarbuja.covidinfo.data.model.Country
import com.ravigarbuja.covidinfo.databinding.ItemCountryListBinding
import java.util.*

class CountryListAdapter constructor(
    private val countryListViewModel: CountryListViewModel,
    private val countryList: MutableList<Country> = arrayListOf()
) : RecyclerView.Adapter<CountryListAdapter.CountryItemViewHolder>(), Filterable {

    private var countryListFiltered: MutableList<Country> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemCountryListBinding.inflate(layoutInflater, parent, false)
        return CountryItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return countryListFiltered.size
    }

    inner class CountryItemViewHolder(private val binding: ItemCountryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            with(binding) {
                countryModel = countryListFiltered[position]
                root.setOnClickListener {
                    countryListViewModel.getNavigator().onItemClick(countryModel as Country)
                }
                executePendingBindings()
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                countryListFiltered.clear()
                if (charString.isEmpty()) {
                    countryListFiltered.addAll(countryList)
                } else {
                    for (row in countryList) {
                        // name match condition. this might differ depending on your requirement
                        if (row.name.toLowerCase().contains(charString.toLowerCase())) {
                            countryListFiltered.add(row)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = countryListFiltered
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                countryListFiltered = filterResults.values as MutableList<Country>
                notifyDataSetChanged()
            }
        }
    }

    fun setCountryList(list: List<Country>) {
        this.countryList.clear()
        this.countryListFiltered.clear()
        this.countryList.addAll(list)
        this.countryListFiltered.addAll(list)
        notifyDataSetChanged()
    }

    fun sortByCases() {
        countryListFiltered.sortWith(Comparator { o1, o2 ->
            o2.totalConfirmed.compareTo(o1.totalConfirmed);
        })
        notifyDataSetChanged()
    }

    fun sortByDeaths() {
        countryListFiltered.sortWith(Comparator { o1, o2 ->
            o2.totalDeaths.compareTo(o1.totalDeaths);
        })
        notifyDataSetChanged()
    }

    fun sortByRecovered() {
        countryListFiltered.sortWith(Comparator { o1, o2 ->
            o2.totalRecovered.compareTo(o1.totalRecovered);
        })
        notifyDataSetChanged()
    }

}
