package com.khane.ui.manager

import com.khane.ui.base.BaseActivity
import com.khane.ui.base.BaseFragment
import com.khane.ui.imagepicker.ImageVideoPickerDialog


/**
 * Created by hlink21 on 29/5/17.
 */

interface Navigator {

    fun <T : BaseFragment<*>> load(tClass: Class<T>): FragmentActionPerformer<T>

    fun loadActivity(aClass: Class<out BaseActivity>): ActivityBuilder

    fun <T : BaseFragment<*>> loadActivity(
        aClass: Class<out BaseActivity>,
        pageTClass: Class<T>
    ): ActivityBuilder

    fun goBack()

    fun finish()

    fun showErrorMessage(message: String, snackBarListener: BaseActivity.SnackBarListener? = null)

    fun openImageOrVideoPickDefault(
        isCrop: Boolean,
        isCircularCrop: Boolean,
        isVideo: Boolean,
        imageVideoPickerListener: ImageVideoPickerDialog.ImageVideoPickerListener,
    )
}
