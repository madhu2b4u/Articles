package com.example.article.articles.data.models

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("imageHref")
    val imageHref: String
)
