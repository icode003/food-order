package com.khane.ui.module.main.home.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.khane.databinding.HomeFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.ui.base.BaseFragment

class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
   }

    override fun bindData() {
    }

}