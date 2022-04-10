package com.khane.ui.module.authentication.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.khane.databinding.ChooseLanguageFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.ui.base.BaseFragment

class ChooseLanguageFragment : BaseFragment<ChooseLanguageFragmentBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ChooseLanguageFragmentBinding {
        return ChooseLanguageFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        mBinding.buttonArabic.setOnClickListener { moveToNext() }
        mBinding.buttonEnglish.setOnClickListener { moveToNext() }
    }

    private fun moveToNext() {
        navigator.load(SignUpLoginFragment::class.java).replace(true)
    }

}