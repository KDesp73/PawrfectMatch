package io.github.kdesp73.petadoption

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import androidx.core.os.ConfigurationCompat
import java.util.Locale


class LocaleManager {
    companion object {
        private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

        fun getLocale() : Locale? {
            return ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
        }

        // the method is used to set the language at runtime
        fun setLocale(context: Context, language: String): Context {
            persist(context, language)

            // updating the language for devices above android nougat
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResources(context, language)
            } else updateResourcesLegacy(context, language)
            // for devices having lower version of android os
        }

        private fun persist(context: Context, language: String) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString(Companion.SELECTED_LANGUAGE, language)
            editor.apply()
        }

        // the method is used update the language of application by creating
        // object of inbuilt Locale class and passing language argument to it
        private fun updateResources(context: Context, language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            return context.createConfigurationContext(configuration)
        }

        @Suppress("deprecation")
        private fun updateResourcesLegacy(context: Context, language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val resources = context.resources
            val configuration = resources.configuration
            configuration.locale = locale
            configuration.setLayoutDirection(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
            return context
        }
    }
}

