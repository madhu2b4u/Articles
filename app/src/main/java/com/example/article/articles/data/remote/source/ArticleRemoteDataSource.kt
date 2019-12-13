package com.example.article.articles.data.remote.source

import com.example.article.articles.data.models.ArticlesResponse

interface ArticleRemoteDataSource {
    suspend fun getArticles(): ArticlesResponse
}