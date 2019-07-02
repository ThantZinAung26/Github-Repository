package com.soft.pagingdemogithub.api

import android.util.Log
import com.soft.pagingdemogithub.model.GithubDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

private const val TAG = "GithubService"
private const val IN_QUALIFIER = "in:name,description"

interface GithubService {

    fun githubSearch(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Call<GithubSearchResponse>

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService::class.java)

        }
    }

}

fun searchGithub(
    service: GithubService,
    page: Int,
    query: String,
    itemsPerPage: Int,
    onSuccess: (github: List<GithubDTO>) -> Unit,
    onError: (error: String) -> Unit
) {
    Log.d(TAG,"query: $query, page: $page, itemsperpage: $itemsPerPage")

    val apiQuery = query + IN_QUALIFIER

    service.githubSearch(apiQuery, page, itemsPerPage).enqueue(
        object : Callback<GithubSearchResponse> {
            override fun onFailure(call: Call<GithubSearchResponse>?, t: Throwable) {
                Log.d(TAG, "fail to get data")
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(call: Call<GithubSearchResponse>?, response: Response<GithubSearchResponse>) {
                Log.d(TAG, "got a response $response")
                if (response.isSuccessful) {
                    val repos = response.body()?.items ?: emptyList()
                    onSuccess(repos)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }

        }
    )

}
