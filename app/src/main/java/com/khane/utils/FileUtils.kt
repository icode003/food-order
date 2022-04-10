package com.khane.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.gun0912.tedpermission.PermissionListener
import java.io.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    fun saveImage(
        context: Context,
        bitmap: Bitmap,
        isDeleteOldImage: Boolean
    ): File? {
        var imageFile: File? = null
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        if (isDeleteOldImage) {
            deletePictureDirFiles(context)
        }
        try {
            imageFile = createDirectoryPictureImageFile(context)
            val fileOutputStream = FileOutputStream(imageFile)
            fileOutputStream.write(byteArrayOutputStream.toByteArray())
            MediaScannerConnection.scanFile(
                context,
                arrayOf(imageFile.path),
                arrayOf("image/*"),
                null
            )
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return imageFile
    }

    private fun deletePictureDirFiles(context: Context) {
        val dir =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            if (children != null && children.size != 0) {
                for (child in children) {
                    File(dir, child).delete()
                }
            }
        }
    }

    fun createDirectoryPictureImageFile(context: Context): File {
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(Date())
        val imageFileName = "jpeg_" + timeStamp + "_"
        val storageDir =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(
            storageDir
                .toString() + File.separator +
                    imageFileName + ".jpg"
        )
    }


    fun createVideoFile(context: Context): File {
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(Date())
        val imageFileName = "video_" + timeStamp + "_"
        val storageDir =
            context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        return File(
            storageDir.toString()
                    + File.separator +
                    imageFileName
                    + ".mp4"
        )
    }

    fun getBitmapFromUrl(
        context: Context,
        urlPath: String,
        onGetBitmapListener: com.khane.constance.Callbacks.OnGetBitmapListener
    ) {

        PermissionUtils.askReadWriteStoragePermission(context, object : PermissionListener {
            override fun onPermissionGranted() {
                Glide
                    .with(context)
                    .asBitmap()
                    .load(urlPath)
                    .into(object : CustomTarget<Bitmap>() {

                        override fun onLoadCleared(placeholder: Drawable?) {

                        }

                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            onGetBitmapListener.onSuccess(resource, null)
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            onGetBitmapListener.onFail(errorDrawable, null)
                        }


                    })
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                onGetBitmapListener.onFail(null, null)
            }
        })
    }

    fun splitFileName(fileName: String): Array<String> {
        var name = fileName
        var extension = ""
        val i = fileName.lastIndexOf(".")
        if (i != -1) {
            name = fileName.substring(0, i)
            extension = fileName.substring(i)
        }
        return arrayOf(name, extension)
    }

    fun getFileNameFromUri(
        context: Context,
        uri: Uri
    ): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor =
                context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf(File.separator)
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }


    fun readStringFromFile(context: Context, fileRawResId: Int): String {
        return try {
            val inputStream = context.resources.openRawResource(fileRawResId)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    // Bhargav Savasani : 20/05/21 getReadableFileSize(compressFile.length())
    fun getReadableFileSize(size: Long): String {
        if (size <= 0) {
            return "0"
        }
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups =
            (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(
            size / Math.pow(
                1024.0,
                digitGroups.toDouble()
            )
        ) + " " + units[digitGroups]
    }

    @Suppress("DEPRECATION")
    fun getRealPathFromURI(
        context: Context,
        uri: Uri
    ): String? {
        val cursor =
            context.contentResolver.query(uri, null, null, null, null)
        return if (cursor == null) {
            uri.path
        } else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            val realPath = cursor.getString(index)
            cursor.close()
            realPath
        }
    }
}