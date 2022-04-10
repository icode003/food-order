package com.khane.ui.module.authentication.fragment

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import com.khane.databinding.TermsAndConditionsFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.ui.base.BaseFragment

class TermsAndConditionsFragment : BaseFragment<TermsAndConditionsFragmentBinding>() {


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): TermsAndConditionsFragmentBinding {
        return TermsAndConditionsFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        mBinding.textViewDesc.movementMethod = ScrollingMovementMethod()
    }

}