package com.example.GithubApp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.GithubApp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class GithubAppDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubAppDao

    companion object {
        @Volatile
        private var INSTANCE: GithubAppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): GithubAppDatabase {
            if (INSTANCE == null) {
                synchronized(GithubAppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GithubAppDatabase::class.java, "githubUserDatabase"
                        )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE as GithubAppDatabase
        }
    }
}