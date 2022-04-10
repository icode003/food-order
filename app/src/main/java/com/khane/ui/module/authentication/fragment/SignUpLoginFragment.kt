package com.khane.ui.module.authentication.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.khane.R
import com.khane.databinding.SignUpLoginFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.ui.base.BaseFragment
import com.khane.ui.base.ViewPagerAdapter

class SignUpLoginFragment : BaseFragment<SignUpLoginFragmentBinding>() {


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): SignUpLoginFragmentBinding {
        return SignUpLoginFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        setUpViewPager()
        mBinding.textViewSignUp.setOnClickListener {
            setSelectedTab(mBinding.textViewSignUp)
            setUnSelectedTab(mBinding.textViewLogIn)
            mBinding.viewPager.setCurrentItem(0, true)
        }
        mBinding.textViewLogIn.setOnClickListener {
            setSelectedTab(mBinding.textViewLogIn)
            setUnSelectedTab(mBinding.textViewSignUp)
            mBinding.viewPager.setCurrentItem(1, true)
        }
    }

    fun setTitle(title: String) {
        mBinding.textViewTitle.text = title
    }

    private fun setUpViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPagerAdapter.addFragment(LoginFragment(), getString(R.string.label_login), null)
        viewPagerAdapter.addFragment(SignUpFragment(), getString(R.string.label_login), null)

        mBinding.viewPager.adapter = viewPagerAdapter
        /*TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getFragmentTitleList()[position]
        }.attach()*/
    }

    private fun setSelectedTab(textView: TextView) {
        textView.setBackgroundColor(getColor(R.color.colorBlueTheme0C))
        setTextAppearance(textView, R.style.MuseoSans900_White_13)
    }

    private fun setUnSelectedTab(textView: TextView) {
        textView.setBackgroundColor(0)
        setTextAppearance(textView, R.style.MuseoSans900_Grey66_13)
    }

}