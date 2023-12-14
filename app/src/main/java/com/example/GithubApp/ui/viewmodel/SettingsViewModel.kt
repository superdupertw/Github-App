package com.example.GithubApp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.GithubApp.data.SettingPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: SettingPreferences): ViewModel() {
    fun getThemeforSettings(): LiveData<Boolean> {
        return pref.getThemeforSetting().asLiveData()
    }
    fun saveThemeforSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeforSetting(isDarkModeActive)
        }
    }
}