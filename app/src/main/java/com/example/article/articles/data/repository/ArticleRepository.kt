package com.example.article.articles.data.repository

import androidx.lifecycle.LiveData
import com.example.article.articles.data.models.ArticlesResponse
import com.example.article.common.Result

interface ArticleRepository {
    suspend fun getArticles(): LiveData<Result<ArticlesResponse>>
}