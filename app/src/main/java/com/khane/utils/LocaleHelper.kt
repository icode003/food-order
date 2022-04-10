package com.khane.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.khane.core.AppPreferences
import java.util.*

object LocaleHelper {

    const val ACCEPT_LANGUAGE = "accept-language"
    const val LANGUAGE_ENGLISH = "en"
    const val LANGUAGE_ARABIC = "ar"


    fun setLocale(context: Context?): Context? {
        val language = getLanguage(context)
        return updateResources(context, getLanguage(context), getCountryIso(language))
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             return updateResources(context, getLanguage(context))
         } else {
             return updateResourcesLegacy(context, getLanguage(context))
         }*/
    }

    private fun getCountryIso(language: String): String {
        return if (language == LANGUAGE_ARABIC) {
            "SAU"
        } else {
            ""
        }
    }

    fun setLocale(context: Context, language: String, countryIso: String) {
        updateResources(context, language, countryIso)

        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             updateResources(context, language)
         } else {
             updateResourcesLegacy(context, language)
         }*/
    }

    fun getLanguage(context: Context?): String {
        val sharedPreferences: SharedPreferences? =
            context?.getSharedPreferences(AppPreferences.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return StringUtils.getString(
            sharedPreferences?.getString(ACCEPT_LANGUAGE, LANGUAGE_ENGLISH), ""
        )
    }

    private fun updateResources(context: Context?, language: String, countryIso: String): Context? {
        context?.let {
            val locale = Locale(language, countryIso)
            Locale.setDefault(locale)
            val configuration: Configuration = it.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            return it.createConfigurationContext(configuration)
        }
        return context
    }

    /*  @SuppressWarnings("deprecation")
      private fun updateResourcesLegacy(context: Context?, language: String): Context? {
          context?.let {
              val locale = Locale(language)
              Locale.setDefault(locale)
              val resources: Resources = it.resources
              val configuration: Configuration = resources.configuration
              configuration.locale = locale
              configuration.setLayoutDirection(locale)
              resources.updateConfiguration(configuration, resources.displayMetrics)
          }
          return context

      }*/
}