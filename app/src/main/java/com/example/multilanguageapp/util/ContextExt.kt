package com.example.multilanguageapp.util

import android.content.Context
import android.os.Build
import java.util.*

fun Context.applyNewLocale(locale: Locale): Context {
    val config = this.resources.configuration
    val sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.locales.get(0)
    } else {
        //Legacy
        config.locale
    }
    if (sysLocale.language != locale.language) {
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            //Legacy
            config.locale = locale
        }
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    return this
}