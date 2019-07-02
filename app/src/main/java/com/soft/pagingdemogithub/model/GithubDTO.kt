package com.soft.pagingdemogithub.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github")
data class GithubDTO(
    @PrimaryKey @field:SerializedName("id") val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val url: String,
    val stars: Int,
    val fork: Int,
    val language: String?
)