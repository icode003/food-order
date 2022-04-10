package com.khane.utils

import android.app.Activity
import android.content.*
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import java.util.*
import java.io.File
import android.content.pm.PackageManager
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import com.khane.R


object OpenAppUsingIntent {

    fun openCallIntent(context: Context, phoneNumber: String) {
        // Bhargav Savasani : 26/3/21   openCallIntent(activity, "+" + phone.getCountryCode() + phone.getPhoneNumber())
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + Uri.encode(phoneNumber.trim()))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: Exception) {

        }
    }

    fun openSMS(context: Context, number: String, message: String) {
        try {
            val sms_uri = Uri.parse("smsto:" + Uri.encode(number.trim()))
            val sms_intent = Intent(Intent.ACTION_SENDTO, sms_uri)
            sms_intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            sms_intent.putExtra("sms_body", message)
            startActivity(context, sms_intent, null)
        } catch (e: Exception) {

        }
    }

    fun copyTextToClipboard(context: Context, string: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", string)
        clipboardManager.setPrimaryClip(clipData)
    }

    fun openMailIntent(context: Context, emailId: String, subject: String, extraText: String) {

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            type = "message/rfc822"
            val uriText = String.format(
                "mailto:%s?subject=%s&body=%s",
                emailId, subject, extraText
            )
            data = Uri.parse(uriText)
        }
        /* val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
         emailIntent.data = Uri.parse("mailto:$emailId")
          emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
          emailIntent.putExtra(Intent.EXTRA_TEXT, extraText)*/
        try {
            context.startActivity(
                Intent.createChooser(
                    emailIntent,
                    context.getString(R.string.label_send_email_via)
                )
            )
        } catch (ex: ActivityNotFoundException) {
            // Bhargav Savasani : 26/3/21 No email app exits
        }
    }

    fun openPdfViewer(uri: String, context: Context) {
        try {
            val file = File(uri)
            val path = Uri.parse(uri)
            val pdfOpenintent = Intent(Intent.ACTION_VIEW)
            pdfOpenintent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            pdfOpenintent.setDataAndType(path, "application/pdf")
            startActivity(context, pdfOpenintent, null)
        } catch (e: Exception) {
            Log.e("PDF", e.message.toString())
        }
    }

    fun openVideoViewer(uri: String, context: Context) {
        try {
            val file = File(uri)
            val path = Uri.parse(uri)
            val pdfOpenintent = Intent(Intent.ACTION_VIEW)
            pdfOpenintent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            pdfOpenintent.setDataAndType(path, "video/*")
            startActivity(context, pdfOpenintent, null)
        } catch (e: Exception) {
            Log.e("PDF", e.message.toString())
        }
    }

    fun openMap(latitude: String, longitude: String, context: Context) {
        try {
            val uri = String.format(
                Locale.ENGLISH,
                "http://maps.google.com/maps?daddr=%f,%f",
                latitude.toDouble(),
                longitude.toDouble()
            )
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(context, intent, null)
        } catch (e: Exception) {
        }

    }

    fun openCustomTabsIntent(activity: Activity?, stringUrl: String) {
        activity?.let {
            val validUrl: String =
                if (stringUrl.contains("http://") || stringUrl.contains("https://")) {
                    stringUrl
                } else {
                    "http://$stringUrl"
                }
            val builder = CustomTabsIntent.Builder()
//        builder.setToolbarColor(ContextCompat.getColor(activity!!, R.color.colorBlueTheme2B))
            val customTabsIntent = builder.build()
            if (isAppInstalled(it, "com.android.chrome")) {
                customTabsIntent.intent.setPackage("com.android.chrome")
            }
            customTabsIntent.launchUrl(it, Uri.parse(validUrl))
        }
    }

    fun openBrowser(website: String, context: Context) {
        var url = String()
        url = website
        try {
            if (!website.startsWith("http://") && !website.startsWith("https://"))
                url = "http://$website"

            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(url)
            startActivity(context, browserIntent, null)
        } catch (e: Exception) {
            if (url.startsWith("http://")) {
                openBrowser(url.replace("http://", "https://"), context)
            }
        }
    }

    fun watchYoutubeVideo(context: Context, id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(id)
        )
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }

    }

    fun openPlayStore(context: Context) {
        val uri: Uri = Uri.parse("market://details?id=com.paidworkout")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(context, goToMarket, null)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                context, Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.paidworkout")
                ), null
            )
        }
    }

    fun openFitInPlayStore(context: Context) {
        val uri: Uri = Uri.parse("market://details?id=com.google.android.apps.fitness")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(context, goToMarket, null)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                context, Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.fitness")
                ), null
            )
        }
    }

    fun checkIfFitInstalled(context: Context): Boolean {
        return try {
            context.packageManager.getPackageInfo(
                "com.google.android.apps.fitness",
                PackageManager.GET_ACTIVITIES
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    fun share(context: Context, contents: String?) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, contents)
        context.startActivity(
            Intent.createChooser(
                sharingIntent,
                context.resources.getString(R.string.label_share)
            )
        )
    }

    fun playVideo(context: Context, videoUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(videoUrl), "video/*")
        context.startActivity(intent)
    }

    fun shareWithImage(
        context: Context,
        contents: String?,
        uri: Uri?
    ) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "image/*"
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, contents)
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(
            Intent.createChooser(
                sharingIntent,
                context.resources.getString(R.string.label_share)
            )
        )
    }

    fun isAppInstalled(
        context: Context?,
        packageUri: String
    ): Boolean {
        context?.let {
            val pm = it.packageManager
            val app_installed: Boolean
            app_installed = try {
                pm.getPackageInfo(packageUri, PackageManager.GET_ACTIVITIES)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
            return app_installed
        }
        return false
    }

}