package com.khane.constance

object AppConstants {

    const val APP_TITLE = "Sawem"

    object SignUpSteps {
        const val ONE = "one"
        const val TWO = "two"
        const val THREE = "three"
        const val FOUR = "four"
        const val FIVE = "five"
        const val SIX = "six"
        const val SEVEN = "seven"
    }

    object RequestCode {
        const val REQUEST_CODE_CAMERA_PHOTO = 1
        const val REQUEST_CODE_CAMERA_VIDEO = 2
        const val REQUEST_CODE_GALLERY_PHOTO = 3
        const val REQUEST_CODE_GALLERY_VIDEO = 4
        const val REQUEST_CODE_LOCATION_SETTING = 5
    }

    object ResponseCode {
        const val SUCCESS = 1
        const val INACTIVE_ACCOUNT = 3
        const val VERIFY_OTP = 4
        const val SOCIAL_ID_NOT_REGISTER = 11
    }
}