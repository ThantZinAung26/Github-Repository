package com.soft.pagingdemogithub.api

import com.google.gson.annotations.SerializedName
import com.soft.pagingdemogithub.model.GithubDTO

data class GithubSearchResponse(
    @field:SerializedName("total_count")val total: Int = 0,
    @field:SerializedName("items")val items: List<GithubDTO> = emptyList(),
    val nextPage: Int? = null
)