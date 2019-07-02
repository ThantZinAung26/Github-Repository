package com.soft.pagingdemogithub.model

import androidx.lifecycle.LiveData

data class GithubSearchResult(
    val data: LiveData<List<GithubDTO>>,
    val networkErrors: LiveData<String>
)