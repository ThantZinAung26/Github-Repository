package com.soft.pagingdemogithub.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soft.pagingdemogithub.model.GithubDTO

class GithubAdapter: ListAdapter<GithubDTO, RecyclerView.ViewHolder>(GITHUB_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val GITHUB_COMPARATOR = object : DiffUtil.ItemCallback<GithubDTO>() {
            override fun areItemsTheSame(oldItem: GithubDTO, newItem: GithubDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubDTO, newItem: GithubDTO): Boolean {
                return oldItem == newItem
            }

        }
    }



}