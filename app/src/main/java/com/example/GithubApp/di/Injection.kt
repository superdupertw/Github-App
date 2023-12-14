package com.example.GithubApp.di

import android.content.Context
import com.example.GithubApp.data.GithubAppRepository
import com.example.GithubApp.data.local.room.GithubAppDatabase
import com.example.GithubApp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): GithubAppRepository {
        val apiService = ApiConfig.getApiService()
        val database = GithubAppDatabase.getInstance(context)
        val dao = database.githubUserDao()
        return GithubAppRepository.getInstance(apiService, dao)
    }
}