package com.soft.pagingdemogithub.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github")
data class GithubDTO(
    @PrimaryKey @field:SerializedName("id") val id: Long,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("full_name") val fullName: String,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("html_url") val url: String,
    @field:SerializedName("stargazers_count") val stars: Int,
    @field:SerializedName("forks_count") val fork: Int,
    @field:SerializedName("language") val language: String?
)