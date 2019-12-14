package com.example.article.articles.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.article.R
import com.example.article.articles.data.models.Article
import kotlinx.android.synthetic.main.article_recycler_item.view.*
import javax.inject.Inject


class ArticlesRecyclerAdapter @Inject constructor() :
    RecyclerView.Adapter<ArticlesRecyclerAdapter.ArticleViewHolder>() {

    private val articles: MutableList<Article> = ArrayList()

    fun populateArticles(articlesList: List<Article>) {
        articles.clear()
        articles.addAll(articlesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(
            inflater.inflate(R.layout.article_recycler_item, parent, false)
        )
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) = holder.bind(articles[position])

    inner class ArticleViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(article: Article) {
            with(itemView) {
                    val title = article.title
                    val desc = article.description
                    val image = article.imageHref

                    imgArticle.setImageURI(image)
                    tvTitle.text = title
                    if (desc == null) tvDescription.text = context.getText(R.string.no_description_available) else tvDescription.text = desc

            }
        }

    }
}