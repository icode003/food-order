package com.khane.ui.module.main.auctionrooms.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.khane.databinding.AuctionRoomsFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.ui.base.BaseFragment

class AuctionRoomsFragment : BaseFragment<AuctionRoomsFragmentBinding>() {


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AuctionRoomsFragmentBinding {
        return AuctionRoomsFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
    }

}