package com.soft.pagingdemogithub.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soft.pagingdemogithub.model.GithubDTO

class GithubAdapter : ListAdapter<GithubDTO, RecyclerView.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GithubViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val githubItem = getItem(position)
        if (githubItem != null) {
            (holder as GithubViewHolder).bind(githubItem)
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<GithubDTO>() {
            override fun areItemsTheSame(oldItem: GithubDTO, newItem: GithubDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubDTO, newItem: GithubDTO): Boolean {
                return oldItem == newItem
            }

        }
    }


}