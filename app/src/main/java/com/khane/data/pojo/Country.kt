package com.khane.data.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Country(
    @SerializedName(com.khane.constance.JsonKeys.DIAL)
    val phoneCode: String,
    @SerializedName(com.khane.constance.JsonKeys.NAME)
    val countryName: String,
    @SerializedName(com.khane.constance.JsonKeys.CODE)
    val code: String
) : Parcelable
