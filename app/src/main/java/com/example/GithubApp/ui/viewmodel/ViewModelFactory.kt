package com.example.GithubApp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.GithubApp.data.GithubAppRepository
import com.example.GithubApp.data.SettingPreferences
import com.example.GithubApp.data.dataStore
import com.example.GithubApp.di.Injection

class ViewModelFactory private constructor(private val pref: SettingPreferences, private val githubAppRepository: GithubAppRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass){
        MainViewModel::class.java -> MainViewModel(pref, githubAppRepository)
        FollowsViewModel::class.java -> FollowsViewModel(githubAppRepository)
        DetailsViewModel::class.java -> DetailsViewModel(githubAppRepository)
        SettingsViewModel::class.java -> SettingsViewModel(pref)
        FavoritesViewModel::class.java -> FavoritesViewModel(githubAppRepository)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(application: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(SettingPreferences.getInstance(application.dataStore), Injection.provideRepository(application.applicationContext))
            }.also { instance = it }
    }
}