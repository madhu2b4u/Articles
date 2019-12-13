package com.example.article.articles.data.remote.services

import com.example.article.articles.data.models.ArticlesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET


interface ArticlesService {

    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getArticlesAsync(): Deferred<Response<ArticlesResponse>>

}