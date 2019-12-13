package com.example.article.articles.data.repository

import androidx.lifecycle.liveData
import com.example.article.common.Result
import com.example.article.articles.data.remote.source.ArticleRemoteDataSource
import com.example.article.articles.data.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {

    override suspend fun getArticles() = liveData {

        emit(Result.loading())
        try {
            val response = remoteDataSource.getArticles()
            emit(Result.success(response))

        } catch (exception: Exception) {
            emit(Result.error(exception.message ?: ""))
        }
    }




}