package com.example.articles.di

import android.app.Application
import com.example.article.ArticlesApp
import com.example.article.articles.di.ArticlesRemoteModule
import com.example.article.articles.di.DomainModule
import com.example.article.articles.di.PresentationModule
import com.example.article.di.ActivityBuilderModule
import com.example.article.di.FragmentBuilderModule
import com.example.article.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton



@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        FragmentBuilderModule::class,
        ActivityBuilderModule::class,
        AppModule::class,
        ArticlesRemoteModule::class,
        PresentationModule::class,
        DomainModule::class
    ]
)
interface ArticleAppComponent : AndroidInjector<ArticlesApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): ArticleAppComponent
    }

    override fun inject(app: ArticlesApp)
}