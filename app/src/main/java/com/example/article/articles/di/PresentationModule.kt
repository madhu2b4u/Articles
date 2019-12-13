package com.example.article.articles.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.article.articles.presentation.viewmodel.ArticlesViewModel
import com.example.article.common.ViewModelFactory
import com.example.article.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {
    @Binds
    abstract fun bindsViewModelFactory(
        viewModelFactory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel::class)
    abstract fun bindsArticleViewModel(articlesViewModel: ArticlesViewModel): ViewModel
}