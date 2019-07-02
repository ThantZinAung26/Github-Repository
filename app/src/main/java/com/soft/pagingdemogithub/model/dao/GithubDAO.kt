package com.soft.pagingdemogithub.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soft.pagingdemogithub.model.GithubDTO

@Dao
interface GithubDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<GithubDTO>)

    @Query("SELECT * FROM github WHERE (name LIKE :query) OR (description LIKE :query) ORDER BY stars DESC, name ASC")
    fun findAll(query: String): LiveData<List<GithubDTO>>

}