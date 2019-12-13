package com.example.article.di

import com.example.article.articles.presentation.ui.fragment.ArticlesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributesArticlesFragment(): ArticlesFragment
}