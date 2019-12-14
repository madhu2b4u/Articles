package com.example.article.articles.presentation.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.article.R
import com.example.article.articles.presentation.ui.adapter.ArticlesRecyclerAdapter
import com.example.article.articles.presentation.viewmodel.ArticlesViewModel
import com.example.article.common.Status
import com.example.article.common.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_articles.*
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration





class ArticlesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var articlesAdapter: ArticlesRecyclerAdapter

    private lateinit var articlesViewModel: ArticlesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            try {
                initViews()
                articlesViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(ArticlesViewModel::class.java)
                articlesViewModel.articleResult.observe(this@ArticlesFragment, Observer {
                    when (it.status) {
                        Status.LOADING -> showLoading()
                        Status.ERROR -> {
                            showErrorView()
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            it.data?.let { article ->
                                toolbar.title = article.title
                                val nonNullArticleList = article.articles.filter { it.description != null || it.title != null|| it.imageHref != null }
                                articlesAdapter.populateArticles(nonNullArticleList)
                            }

                        }

                    }
                })


            } finally {
                // This line might execute after Lifecycle is DESTROYED.
                if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                    // Here, since we've checked, it is safe to run any
                    // Fragment transactions.

                }
            }
        }
    }

    private fun showErrorView() {
        llNoDataLayout.visibility = View.VISIBLE
        articlesRecycler.visibility = View.GONE
        progressBar.visibility = View.GONE
        if (swipeRefresh.isRefreshing)
            swipeRefresh.isRefreshing = false
    }

    private fun initViews() {
        articlesRecycler.layoutManager = LinearLayoutManager(activity)
        articlesRecycler.adapter = articlesAdapter
        swipeRefresh.setOnRefreshListener {
            articlesViewModel.loadArticles()
        }

        val dividerItemDecoration = DividerItemDecoration(
            context, (articlesRecycler.layoutManager as LinearLayoutManager).orientation
        )
        articlesRecycler.addItemDecoration(dividerItemDecoration)


        activity?.let {
            (it as AppCompatActivity).setSupportActionBar(toolbar)
        }
    }

    private fun showLoading() {
        llNoDataLayout.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        articlesRecycler.visibility = View.GONE
    }

    private fun hideLoading() {
        llNoDataLayout.visibility = View.GONE
        progressBar.visibility = View.GONE
        articlesRecycler.visibility = View.VISIBLE
        if (swipeRefresh.isRefreshing)
            swipeRefresh.isRefreshing = false

    }



}
