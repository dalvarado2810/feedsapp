package com.dani.kibernum.data.repository.source

import android.content.Context
import androidx.preference.PreferenceManager

class MyPreference(
    context: Context
) {
    companion object {
        const val PREF_TAG = "kibernum"
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun getStoredAuthToken(): String {
        return prefs.getString(PREF_TAG, "")!!
    }
    fun setStoredToken(query: String) {
        prefs.edit().putString(PREF_TAG, query).apply()
    }
}