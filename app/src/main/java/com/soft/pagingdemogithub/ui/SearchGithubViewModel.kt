package com.soft.pagingdemogithub.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.soft.pagingdemogithub.data.GithubRepository
import com.soft.pagingdemogithub.model.GithubDTO
import com.soft.pagingdemogithub.model.GithubSearchResult

class SearchGithubViewModel(private val repository: GithubRepository): ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val githubResult: LiveData<GithubSearchResult> = Transformations.map(queryLiveData){
        repository.search(it)
    }

    val githubDTOS: LiveData<List<GithubDTO>> = Transformations.switchMap(githubResult){ it -> it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(githubResult){it -> it.networkErrors}

    fun searchGithub(queryString: String){
        queryLiveData.postValue(queryString)
    }

    fun listScroll(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int){
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount){
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.requestMore(immutableQuery)
            }
        }

    }

    fun lastQueryValue(): String? = queryLiveData.value

}


