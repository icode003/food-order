package com.khane.ui.imagepicker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.khane.R
import com.khane.databinding.ImageVideoPickerDialogBinding
import com.khane.ui.base.BaseBottomSheetDialogFragment
import com.khane.utils.FileUtils
import com.khane.utils.PermissionUtils
import com.khane.utils.StringUtils
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File


class ImageVideoPickerDialog : BaseBottomSheetDialogFragment<ImageVideoPickerDialogBinding>() {

    private var mIsCircularCrop = false
    private var mIsVideo = false
    private var mIsCrop = true
    private var mCameraPhotoFile: File? = null
    private var mCameraVideoFile: File? = null
    private var mImageVideoPickerListener: ImageVideoPickerListener? = null
    private lateinit var mCameraPhotoResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var mCameraVideoResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var mGalleryPhotoResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var mGalleryVideoResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var mCropResultLauncher: ActivityResultLauncher<Intent>

    interface ImageVideoPickerListener {
        fun onGetMedia(intent: Intent?, realPath: String)
    }

    fun newInstance(
        isCrop: Boolean,
        isCircularCrop: Boolean,
        isVideo: Boolean,
        imageVideoPickerListener: ImageVideoPickerListener
    ): ImageVideoPickerDialog {
        return ImageVideoPickerDialog()
            .setIsCrop(isCrop)
            .setIsCircularCrop(isCircularCrop)
            .setIsVideo(isVideo)
            .setImageVideoPickerListener(imageVideoPickerListener)
    }

    private fun setIsCrop(isCrop: Boolean): ImageVideoPickerDialog {
        this.mIsCrop = isCrop
        return this
    }

    private fun setImageVideoPickerListener(imageVideoPickerListener: ImageVideoPickerListener): ImageVideoPickerDialog {
        this.mImageVideoPickerListener = imageVideoPickerListener
        return this
    }

    private fun setIsCircularCrop(isCircularCrop: Boolean): ImageVideoPickerDialog {
        this.mIsCircularCrop = isCircularCrop
        return this
    }

    private fun setIsVideo(isVideo: Boolean): ImageVideoPickerDialog {
        this.mIsVideo = isVideo
        return this
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ImageVideoPickerDialogBinding {
        return ImageVideoPickerDialogBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initResultLauncher()
    }

    override fun isCustomHeight(): Boolean = false

    override fun getBottomSheetViewHeight(): Int = 0

    override fun iniControl(savedInstanceState: Bundle?) {
    }

    override fun handleViews() {
    }

    override fun setListeners() {
        mBinding.constraintCamera.setOnClickListener {
            PermissionUtils.askImagePermission(requireContext(), object : PermissionListener {
                override fun onPermissionGranted() {
                    onClickCamera()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                }

            })
        }
        mBinding.constraintGallery.setOnClickListener {
            PermissionUtils.askImagePermission(requireContext(), object : PermissionListener {
                override fun onPermissionGranted() {
                    onClickGallery()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                }

            })
        }
    }

    private fun initResultLauncher() {
        mCameraPhotoResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    onGetCameraPhotoResult(result.data)
                } else {
                    showError()
                }
            }

        mCameraVideoResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    onGetCameraVideoResult(result.data)
                } else {
                    showError()
                }
            }

        mGalleryPhotoResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    onGetGalleryPhotoResult(result.data)
                } else {
                    showError()
                }
            }
        mGalleryVideoResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    onGetGalleryVideoResult(result.data)
                } else {
                    showError()
                }
            }
        mCropResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    onGetCropResult(result.data)
                } else {
                    showError()
                }
            }
    }

    private fun onClickCamera() {
        if (mIsVideo) {
            val cameraVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            mCameraVideoFile = FileUtils.createVideoFile(requireContext())
            mCameraVideoFile?.let {
                val videoURI = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().packageName + ".provider",
                    it
                )
                cameraVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
            }
            cameraVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60)
            mCameraVideoResultLauncher.launch(cameraVideoIntent)
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            mCameraPhotoFile = FileUtils.createDirectoryPictureImageFile(requireContext())
            mCameraPhotoFile?.let {
                val photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().packageName + ".provider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                }
                mCameraPhotoResultLauncher.launch(takePictureIntent)
            }
        }
    }

    private fun onClickGallery() {
        if (mIsVideo) {
            val type = "video/*"
            val cameraVideoIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            cameraVideoIntent.type = type
//            cameraVideoIntent.putExtra("return-data", true)
            mGalleryVideoResultLauncher.launch(cameraVideoIntent)

        } else {
            val pickPhoto =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            mGalleryPhotoResultLauncher.launch(pickPhoto)
        }
    }

    private fun onGetCameraPhotoResult(intent: Intent?) {
        mCameraPhotoFile?.let {
            if (mIsCrop) {
                startSmoothCropping(Uri.fromFile(it))
            } else {
                sendResult(Uri.fromFile(it).path)
            }
        }
    }

    private fun onGetCameraVideoResult(intent: Intent?) {
        mCameraVideoFile?.let {
            sendResult(it.absolutePath)
        }
    }

    private fun onGetGalleryPhotoResult(intent: Intent?) {
        intent?.let { tempIntent ->
            val selectedImage: Uri? = tempIntent.data
            selectedImage?.let { uri ->
                if (mIsCrop) {
                    startSmoothCropping(uri)
                } else {
                    sendResult(uri.path)
                }
            }
        }
    }

    private fun onGetGalleryVideoResult(intent: Intent?) {
        intent?.let { tempIntent ->
            val selectedVideo: Uri? = tempIntent.data
            selectedVideo?.let { uri ->
                val videoPath: String? = getVideoPath(uri)
                videoPath?.let {
                    sendResult(it)
                }
            }
        }
    }

    private fun onGetCropResult(intent: Intent?) {
        val result = CropImage.getActivityResult(intent)
        result?.let {
            sendResult(it.uri.path)
        }
    }

    private fun sendResult(filePath: String?) {
        mImageVideoPickerListener?.onGetMedia(
            Intent(),
            StringUtils.getString(filePath, "")
        )
        this.dismiss()
    }

    private fun showError() {
        showErrorMessage(getString(R.string.error_msg_something_went_wrong_with_media))
        dismiss()
    }

    private fun startSmoothCropping(uri: Uri) {
        if (mIsCircularCrop) {
            val intent = CropImage.activity(uri)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAutoZoomEnabled(true)
                .setAspectRatio(1, 1)
                .setAllowFlipping(false)
                .setAllowRotation(false)
                .setAllowCounterRotation(false)
                .getIntent(requireContext())
            mCropResultLauncher.launch(intent)
        } else {
            val intent = CropImage.activity(uri)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAutoZoomEnabled(true)
                .setAllowFlipping(false)
                .setAspectRatio(1, 1)
                .setAllowRotation(false)
                .setAllowCounterRotation(false).getIntent(requireContext())
            mCropResultLauncher.launch(intent)
        }
    }

    @SuppressLint("Recycle")
    private fun getVideoPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            requireContext().contentResolver.query(uri, projection, null, null, null)

        cursor?.let {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            val columnIndex = it
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return null
    }


}