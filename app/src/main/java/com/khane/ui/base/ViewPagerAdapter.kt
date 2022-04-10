package com.khane.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private var mFragmentList = ArrayList<Fragment>()
    private var mFragmentTitleList = ArrayList<String>()

    fun getFragmentTitleList(): ArrayList<String> {
        return mFragmentTitleList
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }


    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun addFragment(fragment: Fragment, title: String, bundle: Bundle?) {
        fragment.arguments = bundle
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

}