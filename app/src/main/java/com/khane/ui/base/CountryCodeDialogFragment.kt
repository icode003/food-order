package com.khane.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.khane.R
import com.khane.data.pojo.Country
import com.khane.databinding.CountryCodeDialogFragmentBinding
import com.khane.utils.Utils
import com.khane.utils.ViewUtils

class CountryCodeDialogFragment(
    private val onEventListener: (country: Country) -> Unit
) : BaseBottomSheetDialogFragment<CountryCodeDialogFragmentBinding>() {

    private var mCountryList = ArrayList<Country>()
    private var mCountryCodeListAdapter: CountryCodeListAdapter? = null

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): CountryCodeDialogFragmentBinding {
        return CountryCodeDialogFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun isCustomHeight(): Boolean {
        return true
    }

    override fun getBottomSheetViewHeight(): Int {
        return (ViewUtils.getScreenHeight(requireContext()) * 0.85).toInt()
    }

    override fun iniControl(savedInstanceState: Bundle?) {
        mCountryList = Utils.getCountryList(requireContext())
    }

    override fun handleViews() {
        setRecyclerViewData()
    }

    override fun setListeners() {
        setSearchListener()
        mBinding.includeSearchView.searchView.queryHint = getString(R.string.hint_search_country)
    }

    private fun setRecyclerViewData() {
        mBinding.recyclerViewCountryCode.setHasFixedSize(true)
        mBinding.recyclerViewCountryCode.layoutManager = LinearLayoutManager(requireContext())
        mCountryCodeListAdapter =
            CountryCodeListAdapter(requireContext(), onEventListener = { country ->
                onEventListener(country)
                this.dismiss()
            }, onSearchEmpty = { isShowDataUi ->
                if (isShowDataUi) {
                    mBinding.recyclerViewCountryCode.visibility = View.VISIBLE
//                        layoutNoData.visibility = View.GONE
                } else {
//                        recyclerViewCountryCode.visibility = View.VISIBLE
//                        layoutNoData.visibility = View.GONE
                }
            })
        mBinding.recyclerViewCountryCode.adapter = mCountryCodeListAdapter
        mCountryCodeListAdapter?.addList(mCountryList)
    }

    private fun setSearchListener() {
        mBinding.includeSearchView.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mCountryCodeListAdapter?.getFilter(newText.toString())
                return false
            }

        })
    }
}