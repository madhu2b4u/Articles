package com.example.article.articles.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.article.articles.data.models.ArticlesResponse
import com.example.article.articles.domain.GetArticlesUseCase
import com.example.article.common.Result
import kotlinx.coroutines.launch
import javax.inject.Inject


class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {


//if you don't need to change your source you can do like this
//    val articleResult = liveData {
//        emitSource(getArticlesUseCase.getArticles())
//    }


    val articleResult = MediatorLiveData<Result<ArticlesResponse>>()


    init {
        loadArticles()
    }

    fun loadArticles() {
        viewModelScope.launch {
            articleResult.addSource(getArticlesUseCase.getArticles()) {
                articleResult.value = it
            }
        }
    }



}