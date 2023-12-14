package com.example.GithubApp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.GithubApp.data.GithubAppRepository
import com.example.GithubApp.data.Result
import com.example.GithubApp.data.remote.response.ItemsItem

class FollowsViewModel(private val githubAppRepository: GithubAppRepository): ViewModel() {

    private val _IsLoad = MutableLiveData<Boolean>()
    val IsLoad: LiveData<Boolean> = _IsLoad

    private val _followingList = MutableLiveData<List<ItemsItem>?>()
    val followingList: LiveData<List<ItemsItem>?> = _followingList

    private val _followerList = MutableLiveData<List<ItemsItem>?>()
    val followerList: LiveData<List<ItemsItem>?> = _followerList

    private val _IsEmpty = MutableLiveData<Boolean>()
    val IsEmpty: LiveData<Boolean> = _IsEmpty
    fun getFollowers(username: String) {
        githubAppRepository.getFollowers(username).observeForever{ result->
            when (result) {
                is Result.Loading -> {
                    _IsLoad.value = true
                    _IsEmpty.value = false
                }
                is Result.Success -> {
                    _IsLoad.value = false
                    _IsEmpty.value = false
                    _followerList.value = result.data
                }
                is Result.Error -> {
                    _IsLoad.value = false
                    _IsEmpty.value = false
                }
                is Result.Empty -> {
                    _IsLoad.value = false
                    _IsEmpty.value = true
                }
            }
        }
    }
    fun getFollowings(username: String) {
        githubAppRepository.getFollowing(username).observeForever{ result->
            when (result) {
                is Result.Loading -> {
                    _IsLoad.value = true
                    _IsEmpty.value = false
                }
                is Result.Success -> {
                    _IsLoad.value = false
                    _IsEmpty.value = false
                    _followingList.value = result.data
                }
                is Result.Error -> {
                    _IsLoad.value = false
                    _IsEmpty.value = false
                }
                is Result.Empty -> {
                    _IsLoad.value = false
                    _IsEmpty.value = true
                }
            }
        }
    }
}