package com.example.mvvmarchitecture.ui.countries


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.data.model.Country
import com.example.mvvmarchitecture.databinding.CountryItemLayoutBinding
import com.example.mvvmarchitecture.utils.OnItemClickListener

class CountryAdapter(
    private val countryList: ArrayList<Country>
) : RecyclerView.Adapter<CountryAdapter.DataViewHolder>() {
    lateinit var itemClickListener: OnItemClickListener<Country>

    class DataViewHolder(private val binding: CountryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country, itemClickListener: OnItemClickListener<Country>) {
            binding.textViewCountry.text = country.name
            binding.textViewCountry.setOnClickListener {
                itemClickListener(country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            CountryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = countryList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(countryList[position], itemClickListener)

    fun addData(list: List<Country>) {
        countryList.addAll(list)
    }

}
