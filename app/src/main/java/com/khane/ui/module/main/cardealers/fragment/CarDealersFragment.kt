package com.khane.ui.module.main.cardealers.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.khane.databinding.CarDealersFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.ui.base.BaseFragment

class CarDealersFragment : BaseFragment<CarDealersFragmentBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): CarDealersFragmentBinding {
        return CarDealersFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
   }

    override fun bindData() {
    }

}