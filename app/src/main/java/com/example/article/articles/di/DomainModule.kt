package com.example.article.articles.di

import com.example.article.articles.data.repository.ArticleRepository
import com.example.article.articles.data.repository.ArticleRepositoryImpl
import com.example.article.articles.domain.GetArticlesUseCase
import com.example.article.articles.domain.GetArticlesUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DomainModule {

    @Binds
    abstract fun bindsRepository(
        repoImpl: ArticleRepositoryImpl
    ): ArticleRepository


    @Binds
    abstract fun bindsArticlesUseCase(
        articleUseCase: GetArticlesUseCaseImpl
    ): GetArticlesUseCase




}