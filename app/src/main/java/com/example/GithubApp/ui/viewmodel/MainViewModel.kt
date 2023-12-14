package com.example.GithubApp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.GithubApp.data.GithubAppRepository
import com.example.GithubApp.data.Result
import com.example.GithubApp.data.SettingPreferences
import com.example.GithubApp.data.remote.response.ItemsItem

class MainViewModel(private val pref: SettingPreferences, private val githubAppRepository: GithubAppRepository): ViewModel() {

    private val _IsEmpty = MutableLiveData<Boolean>()
    val IsEmpty: LiveData<Boolean> = _IsEmpty

    private val _isLoad = MutableLiveData<Boolean>()
    val isLoad: LiveData<Boolean> = _isLoad

    private val _userList = MutableLiveData<List<ItemsItem>?>()
    val UserList: LiveData<List<ItemsItem>?> = _userList

    init {
        getUser("Daffatungga")
    }
    fun getUser(username: String) {
        githubAppRepository.getUser(username).observeForever{ result ->
            when (result) {
                is Result.Loading -> {
                    _isLoad.value = true
                    _IsEmpty.value = false
                }
                is Result.Success -> {
                    _isLoad.value = false
                    _IsEmpty.value = false
                    _userList.value = result.data
                }
                is Result.Error -> {
                    _isLoad.value = false
                    _IsEmpty.value = false
                }
                is Result.Empty -> {
                    _isLoad.value = false
                    _IsEmpty.value = true
                }
            }
        }
    }
    fun getThemeforSetting(): LiveData<Boolean> {
        return pref.getThemeforSetting().asLiveData()
    }
}