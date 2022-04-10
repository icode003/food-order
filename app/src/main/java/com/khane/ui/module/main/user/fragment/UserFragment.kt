package com.khane.ui.module.main.user.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.khane.databinding.UserFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.ui.base.BaseFragment

class UserFragment : BaseFragment<UserFragmentBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): UserFragmentBinding {
        return UserFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
    }

}