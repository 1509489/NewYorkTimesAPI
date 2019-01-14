package com.pixelart.newyorktimesapi.ui.homescreen

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pixelart.newyorktimesapi.App
import com.pixelart.newyorktimesapi.R
import com.pixelart.newyorktimesapi.adapter.ArticlesRVAdapter
import com.pixelart.newyorktimesapi.common.*
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.di.activity.ActivityModule

import com.pixelart.newyorktimesapi.ui.detailscreen.ArticleDetailActivity
import com.pixelart.newyorktimesapi.ui.detailscreen.ArticleDetailFragment
import com.pixelart.newyorktimesapi.ui.homescreen.dialog.FilterFragment
import kotlinx.android.synthetic.main.activity_article_list.*
import kotlinx.android.synthetic.main.article_list.*
import javax.inject.Inject


class ArticleListActivity : AppCompatActivity(), ArticlesRVAdapter.OnItemClickedListener, FilterFragment.OnInputListener {

    @Inject lateinit var homeViewModel: HomeViewModel

    private var twoPane: Boolean = false
    private lateinit var rvAdapter: ArticlesRVAdapter
    private lateinit var docs: List<Doc>
    private var searchQuery = "news"
    private var searchFilter = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)
        setSupportActionBar(toolbar)
        toolbar.title = title

        injectDependencies()

        rvAdapter = ArticlesRVAdapter(this)
        if (savedInstanceState != null){
            searchQuery = savedInstanceState.getString(SEARCH_QUERY_KEY)!!
            searchFilter = savedInstanceState.getString(SEARCH_QUERY_FILTER_KEY)!!

            homeViewModel.getArticles(searchQuery, searchFilter,
                "", "", "", 0).observe(this, Observer {

                rvAdapter.submitList(it.docs)
                docs = it.docs

            })
        } else{
            homeViewModel.getArticles(searchQuery, searchFilter,
                "", "", "", 0).observe(this, Observer {

                rvAdapter.submitList(it.docs)
                docs = it.docs

            })
        }


        fab.setOnClickListener { view ->
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
            //.inject(this)
           .newActivityComponent(ActivityModule(this))
        activityComponent.injectHomeScreen(this)

       /* DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()
            .injectHomeScreen(this)*/
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ArticleListActivity)
            addItemDecoration(DividerItemDecoration(this@ArticleListActivity, LinearLayoutManager.VERTICAL))
            adapter = rvAdapter
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
        homeViewModel.getArticles(searchQuery, searchFilter,
            "", "", "", 0).observe(this, Observer {
            rvAdapter.submitList(it.docs)
        })
    }

    fun search(view: View){
        when(view.id){
            R.id.imbSearch ->{
                val query = etSearch.text.toString()
                searchQuery = query
                homeViewModel.getArticles(query, searchFilter,
                    "", "", "", 0).observe(this, Observer {
                    rvAdapter.submitList(it.docs)
                })
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_QUERY_KEY, etSearch.text.toString())
        outState.putString(SEARCH_QUERY_FILTER_KEY, searchFilter)
    }
}
