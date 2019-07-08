package com.soft.pagingdemogithub.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView.*
import com.soft.pagingdemogithub.R
import com.soft.pagingdemogithub.di.Injection
import com.soft.pagingdemogithub.model.GithubDTO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchGithubViewModel
    private val adapter = GithubAdapter()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
            .get(SearchGithubViewModel::class.java)

        setupScrollListener()

        initAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.searchGithub(query)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(query).also { query -> initSearch(query) }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        searchView = findViewById(R.id.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                        updateFromInput()
                        true
                    } else {
                        false
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue())
    }

    private fun initSearch(query: String) {

        searchView = findViewById(R.id.search)

        if (searchView.imeOptions == EditorInfo.IME_ACTION_GO) {
            updateFromInput()
        }

        searchView.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                updateFromInput()
                true
            } else {
                false
            }
        }

        searchView.setQuery(query, true)
        searchView.clearFocus()

        /*search_git.setText(query)
        search_git.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateFromInput()
                true
            } else {
                false
            }
        }

        search_git.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateFromInput()
                true
            } else {
                false
            }
        }*/

    }

    private fun initAdapter() {
        recyclerView.adapter = adapter
        viewModel.githubDTOS.observe(this, Observer<List<GithubDTO>> {
            Log.d("_Activity", "list:${it?.size}")
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })
        viewModel.networkErrors.observe(
            this,
            Observer<String> { Toast.makeText(this, "\\uD83D\\uDE28 Wooops $it", Toast.LENGTH_LONG).show() })
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

    private fun updateFromInput() {

        var query = ""
        query.trim().let {
            if (it.isNotEmpty()) {
                recyclerView.scrollToPosition(0)
                viewModel.searchGithub(it.toString())
                adapter.submitList(null)
            }
        }
        searchView.setQuery(query, true)
        searchView.clearFocus()

    }

    private fun setupScrollListener() {
        val layoutManager = recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItmeCount = layoutManager.findLastVisibleItemPosition()

                viewModel.listScroll(visibleItemCount, lastVisibleItmeCount, totalItemCount)
            }
        })
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }
}
