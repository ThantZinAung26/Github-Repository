package com.soft.pagingdemogithub.model.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.soft.pagingdemogithub.model.GithubDTO
import com.soft.pagingdemogithub.model.dao.GithubDAO
import java.util.concurrent.Executor

class GithubRepo(
    private val githubDAO: GithubDAO,
    private val ioExecutor: Executor
) {
    fun insert(github: List<GithubDTO>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("GithubRepo", "inserting ${github.size} github")
            githubDAO.insert(github)
            insertFinished()
        }
    }

    fun findByName(name: String): LiveData<List<GithubDTO>> {
        val query = "%${name.replace(' ', '%')}%"
        return githubDAO.findAll(query)
    }
}