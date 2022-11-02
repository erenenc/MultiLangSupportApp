package com.example.multilanguageapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.multilanguageapp.R
import com.example.multilanguageapp.databinding.Fragment1Binding
import com.example.multilanguageapp.databinding.Fragment3Binding
import io.paperdb.Paper

class Fragment3 : Fragment() {

    private var _binding: Fragment3Binding? = null
    val binding get() = _binding!!

    private val languages = arrayOf("ENG", "TUR", "ESP", "DE", "SYSTEM")
    private val languageCodes = arrayOf("en", "tr", "es", "de", "sys")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = Fragment3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleLanguageSelection()

    }

    private fun handleLanguageSelection() {
        val languageIndex = Paper.book().read("LANG_INDEX", -1)
        if (languageIndex == -1) {
            binding.tvLanguageInfo.text = resources.getString(R.string.system)
        } else {
            binding.tvLanguageInfo.text = languages[languageIndex]
        }

        binding.btnLang.setOnClickListener {
            val langSelectorBuilder = AlertDialog.Builder(requireContext())
            langSelectorBuilder.setSingleChoiceItems(languages, -1) { dialog, selection ->

                binding.tvLanguageInfo.text = languages[selection]
                Paper.book().write("LANG_INDEX", selection)

                Log.d("erenlanguage", "SettingsFragment: AlertDialog languageCode : ${languageCodes[selection]} --- language : ${languages[selection]}")

                requireActivity().recreate()
                dialog.dismiss()
            }
            langSelectorBuilder.create().show()
        }
    }

}