package com.example.article

import com.example.article.articles.data.models.Article
import com.example.article.articles.data.models.ArticlesResponse

class TestUtils {


    private val fakeArticles = listOf(
        Article(
            "Beavers",
            "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony ",
            "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
        )
    )

    val articlesResponse = ArticlesResponse("About Canada", fakeArticles)
}