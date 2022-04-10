package com.khane.utils

import java.text.SimpleDateFormat
import java.util.*

object ConvertDateTimeFormat {

    fun convertLocalToUtcDate(
        date: String,
        baseFormat: String,
        convertFormat: String
    ): String {
        try {
            // Convert to local
            val format =
                SimpleDateFormat(baseFormat, Locale.ENGLISH)
            format.timeZone = TimeZone.getDefault()
            val d = format.parse(date)

            // Convert to utc
            val serverFormat =
                SimpleDateFormat(convertFormat, Locale.ENGLISH)
            serverFormat.timeZone = TimeZone.getTimeZone("UTC")
            val finalDate = serverFormat.format(d!!)
            return finalDate.replace("AM", "am").replace("PM", "pm")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    fun convertDate(
        date: String,
        dtformat: String,
        needFormat: String,
        isSmallLetter: Boolean
    ): String {
        try {
            val format = SimpleDateFormat(dtformat, Locale.ENGLISH)
            val d = format.parse(date)
            val serverFormat =
                SimpleDateFormat(needFormat, Locale.ENGLISH)
            var finalDate = serverFormat.format(d!!)
            if (isSmallLetter) {
                finalDate = finalDate.replace("AM", "am").replace("PM", "pm")
            }
            return finalDate
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun convertUTCToLocalDate(
        date: String,
        baseFormat: String,
        convertFormat: String
    ): String {
        try {
            // Convert to UTC
            val format =
                SimpleDateFormat(baseFormat, Locale.ENGLISH)
            format.timeZone = TimeZone.getTimeZone("UTC")
            val d = format.parse(date)
            // Convert to LOCAL
            val serverFormat =
                SimpleDateFormat(convertFormat, Locale.ENGLISH)
            serverFormat.timeZone = TimeZone.getDefault()
            val finalDate = serverFormat.format(d!!)
            return finalDate.replace("AM", "am").replace("PM", "pm")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return date
    }

    fun convertUTCToTimezone(
        date: String?,
        baseFormat: String,
        needFormat: String,
        timezone: String?
    ): String {
        try {

            //            String fullDateString = DateTimeUtil.getFullDate(date, baseFormat); /*30-12-2018 08:00*/
/*01-01-1970 08:00*/ // sagar : 28/12/18 If the year date is less than 1990, it is not working here!
// sagar : 28/12/18 Otherwise, only above one line is enough instead of all string builder stuffs
            val stringDate = StringBuilder()
            stringDate.append("30-12-2018 ") /*30-12-2018 */
            stringDate.append(date)
            val stringBaseFormat = StringBuilder()
            stringBaseFormat.append("dd-MM-yyyy ")
            stringBaseFormat.append(baseFormat)
            val sourceFormat =
                SimpleDateFormat(stringBaseFormat.toString(), Locale.ENGLISH)
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC")
            val parsed = sourceFormat.parse(stringDate.toString())
            val tz = TimeZone.getTimeZone(timezone)
            val destFormat = SimpleDateFormat(needFormat, Locale.ENGLISH)
            destFormat.timeZone = tz
            return destFormat.format(parsed!!)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

}