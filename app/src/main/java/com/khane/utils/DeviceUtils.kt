package com.khane.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.text.TextUtils
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*

object DeviceUtils {
    val deviceVersion: Int
        get() {
            return Build.VERSION.SDK_INT
        }

    @SuppressLint("HardwareIds")
    fun getDeviceMAC(context: Context): String {
        val manager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
        val info = manager!!.connectionInfo
        return info.macAddress
    }

    /**
     * Get IP address from first non-localhost interface
     * @param ipv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    fun getIPAddress(useIPv4: Boolean = true): String {
        try {
            val interfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            interfaces.forEach { intf ->

                val addrs: List<InetAddress> = Collections.list(intf.inetAddresses)
                addrs.forEach { addr ->

                    if (!addr.isLoopbackAddress) {
                        val sAddr = addr.hostAddress.toUpperCase()
                        val isIPv4 = addr is Inet4Address
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr
                        } else {
                            if (!isIPv4) {
                                val delim = sAddr.indexOf('%') // drop ip6 port suffix
                                return if (delim < 0) sAddr else sAddr.substring(0, delim)
                            }
                        }
                    }
                }

            }
        } catch (ex: Exception) {
        } // for now eat exceptions
        return "";
    }

    /**
     * Returns the consumer friendly device name
     */
    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else capitalize(manufacturer) + " " + model
        }

    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }

        return phrase.toString()
    }


}