package com.khane.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.LinearLayout
import com.khane.R
import com.khane.utils.getColorFromResource

import android.view.LayoutInflater
import com.khane.databinding.LoadingDialogBinding


class LoadingDialog(context: Context) : Dialog(context) {
    private lateinit var mBinding: LoadingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        mBinding = LoadingDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(mBinding.root)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window?.setGravity(Gravity.CENTER)
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        window?.setBackgroundDrawable(ColorDrawable(context.getColorFromResource(R.color.colorBlack1A)))
        initControls()
    }

    private fun initControls() {

    }
}