package com.pixelart.newyorktimesapi.ui.homescreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pixelart.newyorktimesapi.App
import com.pixelart.newyorktimesapi.R
import com.pixelart.newyorktimesapi.adapter.ArticlesRVAdapter
import com.pixelart.newyorktimesapi.adapter.ArticlesRVPagedListAdapter
import com.pixelart.newyorktimesapi.common.*
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.repository.ArticleDataSource
import com.pixelart.newyorktimesapi.databinding.ActivityArticleListBinding
import com.pixelart.newyorktimesapi.di.activity.ActivityModule

import com.pixelart.newyorktimesapi.ui.detailscreen.ArticleDetailActivity
import com.pixelart.newyorktimesapi.ui.detailscreen.ArticleDetailFragment
import com.pixelart.newyorktimesapi.ui.homescreen.dialog.FilterFragment
import kotlinx.android.synthetic.main.activity_article_list.*
import kotlinx.android.synthetic.main.article_list.*
import javax.inject.Inject


class ArticleListActivity : AppCompatActivity(), ArticlesRVAdapter.OnItemClickedListener,
    ArticlesRVPagedListAdapter.OnItemClickedListener, FilterFragment.OnInputListener{

    //@Inject lateinit var homeViewModel: HomeViewModel
    @Inject lateinit var pagedViewModel: HomePagingViewModel
    @Inject lateinit var binding: ActivityArticleListBinding

    private var twoPane: Boolean = false
    private lateinit var rvAdapter: ArticlesRVAdapter
    private lateinit var rvPagedListAdapter: ArticlesRVPagedListAdapter
    private lateinit var docs: List<Doc>
    private var searchQuery = "news"
    private var searchFilter = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_article_list)
        setSupportActionBar(toolbar)

        injectDependencies()
        binding.toolbar.title = title

        binding.setLifecycleOwner(this)
        //binding.viewModel = homeViewModel
        binding.pagedViewModel = pagedViewModel
        rvAdapter = ArticlesRVAdapter(this)
        rvPagedListAdapter = ArticlesRVPagedListAdapter(this)
    
        if (savedInstanceState != null){
            searchQuery = savedInstanceState.getString(SEARCH_QUERY_KEY)!!
            searchFilter = savedInstanceState.getString(SEARCH_QUERY_FILTER_KEY)!!
        
            pagedViewModel.setQuery(searchQuery)
            pagedViewModel.setQueryFilter(searchFilter)
        } else{
            pagedViewModel.setQuery(searchQuery)
            pagedViewModel.setQueryFilter(searchFilter)
        }
    
        pagedViewModel.getArticles().observe(this, Observer<PagedList<Doc>>{
            rvPagedListAdapter.submitList(it)
            docs = it as List<Doc>
        })
        
        pagedViewModel.getState().observe(this, Observer {
            when(it!!){
                ArticleDataSource.State.INITIALLOADING -> {
                    pbInitial.visibility = View.VISIBLE
                    pbNextPage.visibility = View.GONE
                }
                ArticleDataSource.State.LOADING -> pbNextPage.visibility = View.VISIBLE
                
                ArticleDataSource.State.SUCCESS -> {
                    pbInitial.visibility = View.GONE
                    pbNextPage.visibility = View.GONE
                }
                
                ArticleDataSource.State.FAILURE ->{
                    pbInitial.visibility = View.GONE
                    pbNextPage.visibility = View.GONE
                }
            }
        })
        
        //pbNextPage.visibility = View.INVISIBLE

        fab.setOnClickListener {
            val dialog = FilterFragment()
            dialog.isCancelable = true
            dialog.show(supportFragmentManager, "FilterDialog")
        }

        //Check whether in two-pane or single-pane
        if (article_detail_container != null) {
            twoPane = true
        }
        setupRecyclerView(article_list)
    }

    private fun injectDependencies(){
        val activityComponent = (application as App)
            .applicationComponent
           .newActivityComponent(ActivityModule(this))
        activityComponent.injectHomeScreen(this)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val mLayoutManager = LinearLayoutManager(this)

        recyclerView.apply {
            layoutManager = mLayoutManager
            addItemDecoration(DividerItemDecoration(this@ArticleListActivity, LinearLayoutManager.VERTICAL))
            //adapter = rvAdapter
            adapter = rvPagedListAdapter
        }
    }

    override fun itemClickedListener(position: Int) {
        if (twoPane){
            val fragment = ArticleDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ArticleDetailFragment.ARG_WEB_URL, docs[position].webUrl)
                }
            }
            supportFragmentManager.beginTransaction()
                .add(R.id.article_detail_container, fragment)
                .commit()
        }else{
            startActivity(Intent(this, ArticleDetailActivity::class.java).also {
                it.putExtra(ArticleDetailFragment.ARG_WEB_URL, docs[position].webUrl)
            })
        }
    }

    override fun sendData(input: String) {
        searchFilter = input
        pagedViewModel.setQueryFilter(searchFilter)
    }

    fun search(view: View){
        when(view.id){
            R.id.imbSearch ->{
                val query = etSearch.text.toString()
                searchQuery = query
               pagedViewModel.setQuery(searchQuery)
                hideKeyboard()
            }
        }
    }
    
    private fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = this.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_QUERY_KEY, etSearch.text.toString())
        outState.putString(SEARCH_QUERY_FILTER_KEY, searchFilter)
    }
}
