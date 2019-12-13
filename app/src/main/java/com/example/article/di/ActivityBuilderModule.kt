package com.example.article.di


import com.example.article.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    internal abstract fun contributesMainActivity(): MainActivity
}