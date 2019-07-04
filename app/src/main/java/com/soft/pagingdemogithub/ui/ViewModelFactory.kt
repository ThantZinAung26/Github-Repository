package com.soft.pagingdemogithub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.soft.pagingdemogithub.data.GithubRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: GithubRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchGithubViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SearchGithubViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}