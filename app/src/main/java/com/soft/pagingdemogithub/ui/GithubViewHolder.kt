package com.soft.pagingdemogithub.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soft.pagingdemogithub.R
import com.soft.pagingdemogithub.model.GithubDTO

class GithubViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.title)
    private val description: TextView = view.findViewById(R.id.description)
    private val language: TextView = view.findViewById(R.id.language)
    private val stars: TextView = view.findViewById(R.id.star)
    private val fork: TextView = view.findViewById(R.id.fork)

    private var githubDTO: GithubDTO? = null

    init {
        view.setOnClickListener{
            githubDTO?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(githubDTO: GithubDTO?) {
        if (githubDTO == null) {
            val resource = itemView.resources
            name.text = resource.getString(R.string.loading)
            description.visibility = View.GONE
            language.visibility = View.GONE
            stars.text = resource.getString(R.string.unknown)
            fork.text = resource.getString(R.string.unknown)
        } else showGithubData(githubDTO)

    }
    private fun showGithubData(githubDTO: GithubDTO) {
        this.githubDTO = githubDTO
        name.text = githubDTO.fullName

        var descriptionVisibility = View.GONE
        if (description != null) {
            description.text = githubDTO.description
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility
        stars.text = githubDTO.stars.toString()
        fork.text = githubDTO.fork.toString()

        var languageVisibility = View.GONE
        if (language != null) {
            language.text = githubDTO.language
            languageVisibility = View.VISIBLE
        }

        language.visibility = languageVisibility

    }

    companion object {
        fun create(parent: ViewGroup): GithubViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_github_item, parent, false)
            return GithubViewHolder(view)
        }
    }
}