package com.soft.pagingdemogithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soft.pagingdemogithub.di.Injection
import com.soft.pagingdemogithub.model.GithubDTO
import com.soft.pagingdemogithub.ui.GithubAdapter
import com.soft.pagingdemogithub.ui.SearchGithubViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchGithubViewModel
    private val adapter = GithubAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this,Injection.provideViewModelFactory(this))
            .get(SearchGithubViewModel::class.java)

        setupScrollListener()

        initAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.searchGithub(query)
        initSearch(query)


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue())
    }

    private fun initSearch(query: String) {

    }

    private fun initAdapter() {
        recyclerView.adapter = adapter
        viewModel.githubDTOS.observe(this, Observer<List<GithubDTO>> {
            Log.d("_Activity", "list: $(it?.size)")
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })
        viewModel.networkErrors.observe(this, Observer<String> { Toast.makeText(this,"\\uD83D\\uDE28 Wooops $it",Toast.LENGTH_LONG).show() })
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun setupScrollListener() {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItmeCount = layoutManager.findLastVisibleItemPosition()

                viewModel.listScroll(visibleItemCount, lastVisibleItmeCount, totalItemCount)
            }
        })
    }

    companion object {
        private const val LAST_SEARCH_QUERY = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }
}
