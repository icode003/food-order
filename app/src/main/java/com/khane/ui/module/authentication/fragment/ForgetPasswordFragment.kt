package com.khane.ui.module.authentication.fragment

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khane.R
import com.khane.core.Validator
import com.khane.databinding.ForgetPasswordFragmentBinding
import com.khane.di.component.FragmentComponent
import com.khane.exception.ApplicationException
import com.khane.ui.base.BaseFragment
import com.khane.utils.*
import javax.inject.Inject

class ForgetPasswordFragment : BaseFragment<ForgetPasswordFragmentBinding>() {

    @Inject
    lateinit var validator: Validator

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ForgetPasswordFragmentBinding {
        return ForgetPasswordFragmentBinding.inflate(inflater, container, attachToRoot)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        ViewUtils.setStatusBarColor(requireActivity(), getColor(R.color.colorBlueTheme0C))
        mBinding.textInputLayoutCountryCode.editText?.setCountryCode()
        setTextWatcherForEmailPhone()
        mBinding.buttonSend.setOnClickListener { onClickSend() }
    }

    private fun setTextWatcherForEmailPhone() {
        mBinding.textInputLayoutEmailMobile.editText?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.isEmpty()) {
                    mBinding.textInputLayoutEmailMobile.hint =
                        getString(R.string.hint_email_username_or_mobile_number)
                    mBinding.textInputLayoutCountryCode.visibility = View.GONE
                    return
                }
                if (StringUtils.isDigit("$editable")) {
                    mBinding.textInputLayoutEmailMobile.editText?.filters =
                        arrayOf<InputFilter>(InputFilter.LengthFilter(resources.getInteger(R.integer.phone_max_length)))
                    mBinding.textInputLayoutCountryCode.visibility = View.VISIBLE
                    mBinding.textInputLayoutEmailMobile.hint =
                        getString(R.string.hint_mobile_number)
                } else {
                    mBinding.textInputLayoutEmailMobile.editText?.filters =
                        arrayOf<InputFilter>(InputFilter.LengthFilter(resources.getInteger(R.integer.email_max_length)))
                    mBinding.textInputLayoutCountryCode.visibility = View.GONE
                    mBinding.textInputLayoutEmailMobile.hint = getString(R.string.hint_email)
                }
            }
        })
    }

    private fun onClickSend() {
        if (isValidUserInput()) {
            hideKeyBoard()
            navigator.load(VerificationFragment::class.java)
                .setBundle(StringUtils.getSourceScreenBundle(javaClass.simpleName)).replace(true)
        }
    }

    private fun isValidUserInput(): Boolean {
        return try {
            validator.submit(mBinding.textInputLayoutEmailMobile.editText!!)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_enter_email_or_mobile))
                .check()

            if (StringUtils.isDigit(mBinding.textInputLayoutEmailMobile.editText.getString())) {
                validator.submit(mBinding.textInputLayoutEmailMobile.editText!!)
                    .checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_mobile_number))
                    .checkMinDigits(8)
                    .errorMessage(getString(R.string.validation_valid_mobile_number))
                    .checkMaxDigits(11)
                    .errorMessage(getString(R.string.validation_valid_mobile_number))
                    .check()
            } else {
                validator.submit(mBinding.textInputLayoutEmailMobile.editText!!)
                    .checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_email))
                    .checkValidEmail()
                    .errorMessage(getString(R.string.validation_enter_valid_email))
                    .check()
            }
            true
        } catch (e: ApplicationException) {
            super.onError(e)
            false
        }
    }
}