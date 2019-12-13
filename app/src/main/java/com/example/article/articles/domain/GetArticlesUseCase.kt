package com.example.article.articles.domain

import androidx.lifecycle.LiveData
import com.example.article.articles.data.models.ArticlesResponse
import com.example.article.common.Result

interface GetArticlesUseCase {

    suspend fun getArticles(): LiveData<Result<ArticlesResponse>>
}