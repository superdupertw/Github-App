package com.example.GithubApp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.GithubApp.data.GithubAppRepository
import com.example.GithubApp.data.local.entity.UserEntity
import com.example.GithubApp.data.remote.response.DetailUserResponse
import kotlinx.coroutines.launch
import com.example.GithubApp.data.Result

class DetailsViewModel(private val githubAppRepository: GithubAppRepository): ViewModel() {

    private val _IsLoad = MutableLiveData<Boolean>()
    val isLoad: LiveData<Boolean> = _IsLoad

    private val _userDetail = MutableLiveData<DetailUserResponse?>()
    val userDetail: LiveData<DetailUserResponse?> = _userDetail

    private val _entityUser = MutableLiveData<UserEntity>()
    val entityUser: LiveData<UserEntity> = _entityUser

    fun addFavorite(user: UserEntity) {
        viewModelScope.launch {
            githubAppRepository.addFavorite(user)
        }
    }
    fun deleteFavorite(username: String) {
        viewModelScope.launch {
            githubAppRepository.deleteFavorite(username)
        }
    }
//    fun getDetailUser(username: String) = githubUserRepository.getDetailUser(username)
    fun getDetailUser(username: String){
        githubAppRepository.getDetailUser(username).observeForever{ result ->
            when (result) {
                is Result.Loading -> {
                    _IsLoad.value = true
                }
                is Result.Success -> {
                    _IsLoad.value = false
                    _userDetail.value = result.data
                    _entityUser.value = UserEntity(0,result.data.login,result.data.avatarUrl)
                }
                is Result.Error -> {
                    _IsLoad.value = false
                }
                is Result.Empty -> {
                    _IsLoad.value = false
                }
            }
        }
    }

    fun isFavorite(username: String) = githubAppRepository.isFavorite(username)
}