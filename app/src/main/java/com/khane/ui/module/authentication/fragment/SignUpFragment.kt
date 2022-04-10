package com.khane.ui.module.authentication.fragment

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khane.R
import com.khane.core.Validator
import com.khane.data.pojo.Country
import com.khane.databinding.SignUpFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.exception.ApplicationException
import com.khane.ui.base.BaseFragment
import com.khane.ui.base.CountryCodeDialogFragment
import com.khane.utils.StringUtils
import com.khane.utils.getString
import com.khane.utils.makeLinks
import com.khane.utils.setCountryCode
import javax.inject.Inject

class SignUpFragment : BaseFragment<SignUpFragmentBinding>() {

    @Inject
    lateinit var validator: Validator

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): SignUpFragmentBinding {
        return SignUpFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        setTitle()
        setCheckBox()
        mBinding.textInputLayoutCountryCode.editText?.setCountryCode()
        mBinding.textInputLayoutCountryCode.editText?.setOnClickListener { onClickCountryCode() }
        mBinding.buttonCreate.setOnClickListener { onClickCreate() }
    }

    private fun setTitle() {
        (parentFragment as SignUpLoginFragment).setTitle(getString(R.string.label_sign_up))
    }

    private fun setCheckBox() {
        mBinding.checkBoxTermsAndCondition.makeLinks(
            Pair(getString(R.string.label_terms_and_conditions), View.OnClickListener {
                hideKeyBoard()
                navigator.load(TermsAndConditionsFragment::class.java).add(true)
                setCheckboxChecked()
            }),
            Pair(getString(R.string.label_privacy_policy), View.OnClickListener {
                hideKeyBoard()
                navigator.load(PrivacyPolicyFragment::class.java).add(true)
                setCheckboxChecked()
            })
        )
    }

    private fun setCheckboxChecked() {
        if (!mBinding.checkBoxTermsAndCondition.isChecked) {
            Handler(Looper.getMainLooper()).post {
                mBinding.checkBoxTermsAndCondition.isChecked = false
            }
        } else {
            Handler(Looper.getMainLooper()).post {
                mBinding.checkBoxTermsAndCondition.isChecked = true
            }
        }
    }

    private fun onClickCountryCode() {
        val countryCodeDialogFragment =
            CountryCodeDialogFragment(onEventListener = { country: Country ->
                mBinding.textInputLayoutCountryCode.editText?.setText(country.phoneCode)
            })
        countryCodeDialogFragment.show(childFragmentManager, countryCodeDialogFragment.tag)
    }

    private fun onClickCreate() {
        if (isValidUserInput()) {
            hideKeyBoard()
            navigator.load(VerificationFragment::class.java)
                .setBundle(StringUtils.getSourceScreenBundle(javaClass.simpleName)).replace(true)
        }
    }

    private fun isValidUserInput(): Boolean {
        return try {
            validator.submit(mBinding.textInputLayoutUserName.editText!!)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_enter_username))
                .checkMinDigits(2)
                .errorMessage(getString(R.string.validation_enter_minimum_2_char_name))
                .check()
            validator.submit(mBinding.textInputLayoutEmail.editText!!)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_enter_email))
                .checkValidEmail()
                .errorMessage(getString(R.string.validation_enter_valid_email))
                .check()
            validator.submit(mBinding.textInputLayoutMobileNumber.editText!!)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_enter_mobile_number))
                .checkMinDigits(8)
                .errorMessage(getString(R.string.validation_valid_mobile_number))
                .checkMaxDigits(11)
                .errorMessage(getString(R.string.validation_valid_mobile_number))
                .check()
            validator.submit(mBinding.textInputLayoutPassword.editText!!)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_enter_password))
                .checkMinDigits(4)
                .errorMessage(getString(R.string.validation_enter_minimum_4_char_password))
                .check()
            validator.submit(mBinding.textInputLayoutConfirmPassword.editText!!)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_enter_confirm_password))
                /* .checkMinDigits(4)
                 .errorMessage(getString(R.string.validation_enter_minimum_4_char_confirm_password))*/
                .check()
            validator.match(
                mBinding.textInputLayoutPassword.editText!!.getString(),
                mBinding.textInputLayoutConfirmPassword.editText!!.getString(),
                getString(R.string.validation_password_mismatch)
            )
            if (!mBinding.checkBoxTermsAndCondition.isChecked) {
                throw ApplicationException(getString(R.string.validation_please_agree_terms_and_conditions))
            }
            true
        } catch (e: ApplicationException) {
            super.onError(e)
            false
        }
    }
}