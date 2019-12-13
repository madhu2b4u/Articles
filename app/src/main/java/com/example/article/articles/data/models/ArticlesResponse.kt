package com.example.article.articles.data.models

import com.example.article.articles.data.models.Article
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ArticlesResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("rows")
    var articles: List<Article>
):Serializable