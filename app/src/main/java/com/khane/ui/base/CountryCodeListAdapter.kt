package com.khane.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khane.R
import com.khane.data.pojo.Country
import com.khane.databinding.CountryCodeListItemBinding

class CountryCodeListAdapter(
    private val context: Context,
    private val onEventListener: (country: Country) -> Unit,
    private val onSearchEmpty: (isShowDataUi: Boolean) -> Unit
) : RecyclerView.Adapter<CountryCodeListAdapter.CountryCodeListViewHolder>() {

    private var mCountryList = ArrayList<Country>()
    private var mCountryListFiltered = ArrayList<Country>()

    var isShowCode = true

    fun addList(countryList: ArrayList<Country>) {
        this.mCountryList = countryList
        this.mCountryListFiltered.clear()
        this.mCountryListFiltered.addAll((countryList).clone() as ArrayList<Country>)
        notifyDataSetChanged()
    }

    fun clear() {
        mCountryList.clear()
    }

    fun getFilter(searchText: String) {
        if (searchText.isNotEmpty()) {
            val tempList = mCountryListFiltered.filter { country ->
                country.countryName.contains(searchText, true)
            } as ArrayList<Country>
            updateList(tempList)
        } else {
            val tempList = ArrayList<Country>()
            tempList.addAll(mCountryListFiltered)
            updateList(tempList)
        }
    }

    private fun updateList(tempList: ArrayList<Country>) {
        if (tempList.isNotEmpty()) {
            onSearchEmpty(true)
            mCountryList = tempList
            notifyDataSetChanged()
        } else {
            onSearchEmpty(false)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryCodeListAdapter.CountryCodeListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_code_list_item, parent, false)
        return CountryCodeListViewHolder(CountryCodeListItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(
        holder: CountryCodeListAdapter.CountryCodeListViewHolder,
        position: Int
    ) {
        val country = mCountryList[position]

        /*holder.mBinding.circleImageViewPhoto
        GlideUtils.loadImage(
            context,
            Utils.getCountryFlagImage(context,country),
            0,
            0,
            holder.mBinding.circleImageViewPhoto
        )*/
        holder.mBinding.textViewCountry.text = country.countryName
        holder.mBinding.textViewCode.text = country.phoneCode
    }

    override fun getItemCount(): Int {
        return mCountryList.size
    }

    inner class CountryCodeListViewHolder(binding: CountryCodeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mBinding = binding

        init {
            itemView.setOnClickListener {
                onEventListener(mCountryList[adapterPosition])
            }
        }
    }
}