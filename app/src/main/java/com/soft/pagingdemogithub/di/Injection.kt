package com.soft.pagingdemogithub.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.soft.pagingdemogithub.api.GithubService
import com.soft.pagingdemogithub.data.GithubRepository
import com.soft.pagingdemogithub.model.db.GithubDatabase
import com.soft.pagingdemogithub.model.db.GithubRepo
import com.soft.pagingdemogithub.ui.ViewModelFactory
import java.util.concurrent.Executors

object Injection {

    private fun provideCache(context: Context): GithubRepo {
        val database = GithubDatabase.getInstance(context)
        return GithubRepo(database.githubDAO(), Executors.newSingleThreadExecutor())
    }

    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(GithubService.create(), provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }

}