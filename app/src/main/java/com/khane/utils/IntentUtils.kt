package com.khane.utils

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable

object IntentUtils {

    fun getParcel(intent: Intent?): Any? {
        intent?.let { tempIntent ->
            if (tempIntent.hasExtra(com.khane.constance.IntentKeys.PARCEL)) {
                return tempIntent.getParcelableExtra(com.khane.constance.IntentKeys.PARCEL)
            }
        }
        return null
    }

    fun getParcel(intent: Intent?, key: String): Any? {
        intent?.let { tempIntent ->
            if (tempIntent.hasExtra(key)) {
                return tempIntent.getParcelableExtra(key)
            }
        }
        return null
    }

    fun getParcel(bundle: Bundle?): Any? {
        bundle?.let { tempBundle ->
            if (tempBundle.containsKey(com.khane.constance.IntentKeys.PARCEL)) {
                return tempBundle.getParcelable(com.khane.constance.IntentKeys.PARCEL)
            }
        }
        return null
    }

    fun getParcel(bundle: Bundle?, key: String): Any? {
        bundle?.let { tempIntent ->
            if (tempIntent.containsKey(key)) {
                return tempIntent.getParcelable(key)
            }
        }
        return null
    }

    fun getParcelList(intent: Intent?): ArrayList<Parcelable>? {
        intent?.let { tempIntent ->
            if (tempIntent.hasExtra(com.khane.constance.IntentKeys.PARCEL_LIST)) {
                return tempIntent.getParcelableArrayListExtra<Parcelable>(com.khane.constance.IntentKeys.PARCEL_LIST)
            }
        }
        return ArrayList()
    }

    fun getParcelList(intent: Intent?, key: String): ArrayList<Parcelable>? {
        intent?.let { tempIntent ->
            if (tempIntent.hasExtra(key)) {
                return tempIntent.getParcelableArrayListExtra<Parcelable>(key)
            }
        }
        return ArrayList()
    }


    fun getParcelList(bundle: Bundle?): ArrayList<Parcelable>? {
        bundle?.let { tempBundle ->
            if (tempBundle.containsKey(com.khane.constance.IntentKeys.PARCEL_LIST)) {
                return tempBundle.getParcelableArrayList<Parcelable>(com.khane.constance.IntentKeys.PARCEL_LIST)
            }
        }
        return ArrayList()
    }

    fun getParcelList(bundle: Bundle?, key: String): ArrayList<Parcelable>? {
        bundle?.let { tempBundle ->
            if (tempBundle.containsKey(key)) {
                return tempBundle.getParcelableArrayList<Parcelable>(key)
            }
        }
        return ArrayList()
    }


    fun putParcel(parcelable: Any?, intent: Intent) {
        intent.putExtra(com.khane.constance.IntentKeys.PARCEL, parcelable as Parcelable?)
    }

    fun putParcel(parcelable: Any?, intent: Intent?, key: String) {
        intent?.putExtra(key, parcelable as Parcelable?)
    }

    fun putParcel(parcelable: Any?, bundle: Bundle) {
        bundle.putParcelable(com.khane.constance.IntentKeys.PARCEL, parcelable as Parcelable?)
    }

    fun putParcel(parcelable: Any?, bundle: Bundle?, key: String) {
        bundle?.putParcelable(key, parcelable as Parcelable?)
    }

    fun putParcelList(parcelableList: ArrayList<Parcelable>?, intent: Intent?) {
        intent?.putParcelableArrayListExtra(com.khane.constance.IntentKeys.PARCEL, parcelableList)
    }

    fun putParcelList(parcelableList: ArrayList<Parcelable>?, intent: Intent?, key: String) {
        intent?.putParcelableArrayListExtra(key, parcelableList)
    }

    fun putParcelList(parcelableList: ArrayList<Parcelable>?, bundle: Bundle?) {
        bundle?.putParcelableArrayList(com.khane.constance.IntentKeys.PARCEL, parcelableList)
    }

    fun putParcelList(parcelableList: ArrayList<Parcelable>?, bundle: Bundle?, key: String) {
        bundle?.putParcelableArrayList(key, parcelableList)
    }


}