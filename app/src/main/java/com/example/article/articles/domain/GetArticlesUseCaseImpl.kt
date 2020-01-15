package com.example.article.articles.domain

import com.example.article.articles.data.repository.ArticleRepository
import javax.inject.Inject

class GetArticlesUseCaseImpl @Inject constructor(private val articleRepository: ArticleRepository) :
    GetArticlesUseCase {

    override suspend fun getArticles() = articleRepository.getArticles()
}
