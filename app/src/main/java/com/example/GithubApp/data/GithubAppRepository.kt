package com.example.GithubApp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.GithubApp.data.local.entity.UserEntity
import com.example.GithubApp.data.local.room.GithubAppDao
import com.example.GithubApp.data.remote.response.DetailUserResponse
import com.example.GithubApp.data.remote.response.ItemsItem
import com.example.GithubApp.data.remote.retrofit.ApiService

class GithubAppRepository (
    private val apiService: ApiService,
    private val githubAppDao: GithubAppDao
) {
    fun getUser(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(username)
            val items = response.items
            if(items==null) emit(Result.Empty)
            else emit(Result.Success(items))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun getFollowing(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowing(username)
            if(response.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getFollowing: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun getFollowers(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(username)
            if(response.isEmpty()) emit(Result.Empty)
            else emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getFollowers: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun getDetailUser(username: String): LiveData<Result<DetailUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("GithubUserRepository", "getDetailUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun getAllFavorite(): LiveData<List<UserEntity>> {
        return githubAppDao.getAllFavorite()
    }
    fun isFavorite(username: String): LiveData<Boolean> {
        return githubAppDao.isFavorite(username)
    }
    suspend fun addFavorite(user: UserEntity) {
        githubAppDao.addFavorite(user)
    }
    suspend fun deleteFavorite(username: String) {
        githubAppDao.deleteFavorite(username)
    }

    companion object {
        @Volatile
        private var instance: GithubAppRepository? = null
        fun getInstance(
            apiService: ApiService,
            githubAppDao: GithubAppDao
        ): GithubAppRepository =
            instance ?: synchronized(this) {
                instance ?: GithubAppRepository(apiService, githubAppDao)
            }.also { instance = it }
    }
}