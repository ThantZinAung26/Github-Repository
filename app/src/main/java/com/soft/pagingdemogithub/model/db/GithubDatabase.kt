package com.soft.pagingdemogithub.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.soft.pagingdemogithub.model.GithubDTO
import com.soft.pagingdemogithub.model.dao.GithubDAO

@Database(entities = [GithubDTO::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDAO(): GithubDAO

    companion object {

        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getInstance(context: Context): GithubDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    GithubDatabase::class.java, "github.db"
                ).build()
            }
    }

}