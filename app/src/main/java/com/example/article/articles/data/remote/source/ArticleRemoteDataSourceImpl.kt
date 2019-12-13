package com.example.article.articles.data.remote.source

import com.example.article.articles.data.remote.services.ArticlesService
import com.example.article.di.qualifiers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ArticleRemoteDataSourceImpl @Inject constructor(
    private val articlesService: ArticlesService,
    @IO private val context: CoroutineContext) : ArticleRemoteDataSource {
    override suspend fun getArticles() = withContext(context) {
        val response = articlesService.getArticlesAsync().await()

        if (response.isSuccessful)
            response.body()?: throw Exception("no Articles")
        else
            throw Exception("invalid request with code ${response.code()}")
    }


}