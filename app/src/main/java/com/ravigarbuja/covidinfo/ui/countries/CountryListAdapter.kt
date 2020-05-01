package com.ravigarbuja.covidinfo.ui.countries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ravigarbuja.covidinfo.data.network.model.Country
import com.ravigarbuja.covidinfo.databinding.ItemCountryListBinding

class CountryListAdapter(
    private var countryData: List<Country>,
    private val onItemClickListener: CountryListNavigator
) : RecyclerView.Adapter<CountryListAdapter.CountryItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemCountryListBinding.inflate(layoutInflater, parent, false)
        return CountryItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        val dispatchedOrder = countryData[position]
        holder.bind(dispatchedOrder, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return countryData.size
    }

    inner class CountryItemViewHolder(private val binding: ItemCountryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Country, listener: CountryListNavigator) {
            binding.countryModel = item
            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
            binding.executePendingBindings()
        }
    }

}
