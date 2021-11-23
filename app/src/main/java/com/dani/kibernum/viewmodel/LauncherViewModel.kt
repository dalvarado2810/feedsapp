package com.dani.kibernum.viewmodel

import androidx.lifecycle.ViewModel
import com.dani.kibernum.data.repository.source.MyPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    private val sharedPreferences: MyPreference
): ViewModel() {

    fun isLogged(): Boolean {
        return sharedPreferences.getStoredAuthToken().isNotEmpty()
    }

}