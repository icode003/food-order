package com.khane.utils

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.khane.R
import com.khane.core.AppPreferences
import com.khane.data.pojo.Country
import com.khane.ui.imagepicker.ImageVideoPickerDialog
import java.util.ArrayList
import com.khane.BuildConfig
import com.khane.ui.base.CountryCodeDialogFragment

object Utils {

    private var mCountryList = ArrayList<Country>()


    fun isLogin(appPreferences: AppPreferences): Boolean {
        return appPreferences.getBoolean(com.khane.constance.PreferenceKey.IS_LOGIN)
    }

    fun setLogin(appPreferences: AppPreferences, value: Boolean) {
        appPreferences.putBoolean(com.khane.constance.PreferenceKey.IS_LOGIN, value)
    }

    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    // Bhargav Savasani : 14-09-2021 below method call like this
    /*AppUtils.showAlertDialog(
                    context,
                    context.getString(R.string.alert_msg_my_notes_delete),
                    positiveListener = { dialogInterface, i ->

                    },
                    negativeListener = { dialogInterface, i ->

                    }
                )*/
    fun showAlertDialog(
        context: Context,
        title: CharSequence,
        message: CharSequence,
        positiveListener: DialogInterface.OnClickListener,
        negativeListener: DialogInterface.OnClickListener
    ) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton(context.getString(R.string.label_yes)) { dialog, arg1 ->
            dialog.dismiss()
            positiveListener.onClick(dialog, arg1)
        }
        alertDialogBuilder.setNegativeButton(context.getString(R.string.label_no)) { dialog, which ->
            dialog.dismiss()
            negativeListener.onClick(dialog, which)
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun showImageVideoPickerDialog(
        isCrop: Boolean,
        isCircularCrop: Boolean,
        isVideo: Boolean,
        imageVideoPickerListener: ImageVideoPickerDialog.ImageVideoPickerListener,
        fragmentManager: FragmentManager,
    ) {
        val imageVideoPickerDialog =
            ImageVideoPickerDialog().newInstance(
                isCrop,
                isCircularCrop,
                isVideo,
                imageVideoPickerListener
            )
        imageVideoPickerDialog.show(fragmentManager, imageVideoPickerDialog.tag)
    }

    fun showCountryCodeDialog(
        onEventListener: (country: Country) -> Unit,
        fragmentManager: FragmentManager
    ) {
        val countryCodeDialogFragment =
            CountryCodeDialogFragment(onEventListener)
        countryCodeDialogFragment.show(fragmentManager, countryCodeDialogFragment.tag)
    }

    fun getCountryFlagImage(context: Context, country: Country): String {
        return ViewUtils.getURLForResource(
            context,
            BuildConfig.APPLICATION_ID,
            ViewUtils.getResourceId(context, country.code.lowercase())
        )
    }

    fun getCountryList(context: Context): ArrayList<Country> {
        if (mCountryList.isEmpty()) {
            val gson = Gson()
            val jsonArray: JsonArray = gson.fromJson(
                FileUtils.readStringFromFile(context, R.raw.phone_country_code),
                JsonArray::class.java
            )
            mCountryList = JsonUtils.getObjectListFromString(
                gson.toJson(jsonArray),
                Country::class.java
            ) as ArrayList<Country>
        }
        return mCountryList
    }

}