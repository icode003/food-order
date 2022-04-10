package com.khane.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.khane.R

object PermissionUtils {

    private fun checkWriteExternalPermission(
        context: Context?,
        permission: String
    ): Boolean {
        return context?.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun getLocationDeniedMessage(context: Context?): String? {
        return context?.resources?.getString(R.string.msg_no_permission_location)
            ?.replace("#", com.khane.constance.AppConstants.APP_TITLE)
    }

    private fun getCameraPermissionMessage(context: Context?): String? {
        return if (!checkWriteExternalPermission(context, Manifest.permission.CAMERA)) {
            context?.resources?.getString(R.string.msg_no_permission_photos_and_videos)
                ?.replace("#", com.khane.constance.AppConstants.APP_TITLE)
        } else context?.resources?.getString(R.string.msg_storage)
            ?.replace("#", com.khane.constance.AppConstants.APP_TITLE)
    }

    private fun getStoragePermissionMessage(context: Context?): String? {
        return context?.resources?.getString(R.string.msg_storage)
            ?.replace("#", com.khane.constance.AppConstants.APP_TITLE)
    }

    fun askImagePermission(
        context: Context,
        permissionListener: PermissionListener
    ) {

        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedMessage(getCameraPermissionMessage(context))
            .setPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).check()
    }

    fun askReadWriteStoragePermission(
        context: Context?,
        permissionListener: PermissionListener
    ) {
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedMessage(getStoragePermissionMessage(context))
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).check()
    }

    fun askLocationPermissions(
        context: Context?,
        permissionListener: PermissionListener
    ) {

        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedMessage(getLocationDeniedMessage(context))
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .check()
    }

}