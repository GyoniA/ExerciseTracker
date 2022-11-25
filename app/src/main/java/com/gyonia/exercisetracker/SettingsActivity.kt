package com.gyonia.exercisetracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, FragmentSettingsBasic())
            .commit()
    }

    override fun onStart() {
        super.onStart()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)

        super.onStop()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            KEY_AUTO_LOGIN -> {
                with (sharedPreferences.edit()) {
                    putString(KEY_CURRENT_ID, ExerciseApplication.userId)
                    apply()
                }
            }
            KEY_CURRENT_ID -> {

            }
        }
    }

    class FragmentSettingsBasic : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, key: String?) {
            addPreferencesFromResource(R.xml.preferences)
        }
    }

    companion object {
        const val KEY_AUTO_LOGIN = "auto_login"
        const val KEY_CURRENT_ID = "current_id"

        fun getAutoLogin(sharedPreferences: SharedPreferences, context: Context): Boolean {
            return sharedPreferences.getBoolean(KEY_AUTO_LOGIN, false)
        }

        fun getUserId(sharedPreferences: SharedPreferences, context: Context): String {
            return sharedPreferences.getString(KEY_CURRENT_ID, "")!!
        }
    }

}