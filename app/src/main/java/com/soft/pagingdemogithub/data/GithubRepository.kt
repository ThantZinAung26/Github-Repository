package com.soft.pagingdemogithub.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.soft.pagingdemogithub.api.GithubService
import com.soft.pagingdemogithub.api.searchGithub
import com.soft.pagingdemogithub.model.GithubSearchResult
import com.soft.pagingdemogithub.model.db.GithubRepo

class GithubRepository(
    private val service: GithubService,
    private val cache: GithubRepo

) {

    companion object {
        private const val NETWORK_PAGE_SIZE: Int = 50
    }

    private var lastRequestPage = 1

    private val networkErrors = MutableLiveData<String>()

    private var isRequestInProgress = false

    fun search(query: String): GithubSearchResult {

        Log.d("GithubReposotory", "new query: $query")
        lastRequestPage = 1
        val data = cache.findByName(query)
        return GithubSearchResult(data, networkErrors)
    }

    fun requestMore(query: String) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true
        searchGithub(service, lastRequestPage, query, NETWORK_PAGE_SIZE, { gitubs ->
            cache.insert(gitubs) {
                lastRequestPage++
                isRequestInProgress = false
            }
        }, {errors ->
            networkErrors.postValue(errors)
            isRequestInProgress = false

        })
    }

}