package com.example.multilanguageapp

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.multilanguageapp.databinding.ActivityMainBinding
import com.example.multilanguageapp.util.applyNewLocale
import io.paperdb.Paper
import java.util.*

const val TAG = "LANGUAGE_SELECTION"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }


    }


    override fun attachBaseContext(newBase: Context?) {

        val langIndex = Paper.book().read("LANG_INDEX", -1)

        val language = when(langIndex) {
            0 -> {"en"}
            1 -> {"tr"}
            2 -> {"es"}
            3 -> {"de"}
            4 -> {"sys"}
            else -> {"en"}
        }

        // if "sys" is selected as language or nothing is selected (-1), we should set device language as our app's language
        // otherwise we should set selected language as our app's language
        // this number must be same with system index number which is for on this example
        if (langIndex == 4 || langIndex == -1) {
            val deviceLanguage = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)?.language ?: "en"
            Log.d(TAG, "attachBaseContext: IF langIndex : $langIndex /// deviceLanguage : $deviceLanguage ")
            super.attachBaseContext(newBase?.applyNewLocale(Locale(deviceLanguage)))
        } else {
            Log.d(TAG, "attachBaseContext: ELSE langIndex : $langIndex /// language : $language ")
            super.attachBaseContext(newBase?.applyNewLocale(Locale(language)))
        }
    }

}