package com.example.GithubApp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GithubApp.data.GithubAppRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(private val githubAppRepository: GithubAppRepository): ViewModel() {

    fun getAllFavorite() = githubAppRepository.getAllFavorite()
    fun deleteFavorite(username: String) {
        viewModelScope.launch {
            githubAppRepository.deleteFavorite(username)
        }
    }
}