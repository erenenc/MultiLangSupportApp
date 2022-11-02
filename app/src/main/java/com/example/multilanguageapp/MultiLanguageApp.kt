package com.example.multilanguageapp

import android.app.Application
import io.paperdb.Paper

class MultiLanguageApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Paper.init(this)

    }

}