package com.khane.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.snackbar.Snackbar
import com.khane.R
import com.khane.ui.base.BaseActivity


object ErrorMessageUtils {
    fun getErrorSnackBarDefault(
        context: Context,
        view: View,
        message: String,
        snackBarListener: BaseActivity.SnackBarListener?
    ): Snackbar {
        val make = Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_LONG
        )
        make.view.setBackgroundColor(context.getColorFromResource(R.color.colorBlack))
//            make.view.setBackgroundResource(R.drawable.bg_gray_radius_13)
        val textView = make.view.findViewById<AppCompatTextView>(R.id.snackbar_text)
//        textView.typeface = ResourcesCompat.getFont(context, R.font.sf_pro_display_medium)
        textView.setTextColor(context.getColorFromResource(R.color.colorWhite))
        textView.textSize = 16F
        textView.maxLines = 5
        make.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                snackBarListener?.onDismiss()
            }
        })
        return make
    }

    fun getCustomToast(activity: Activity, message: String) {
        val layout: View = activity.layoutInflater.inflate(R.layout.raw_custom_toast, null)

        val text = layout.findViewById<AppCompatTextView>(R.id.textViewMessage) as TextView
        text.text = message

        val toast = Toast(activity)
        toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.TOP, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}