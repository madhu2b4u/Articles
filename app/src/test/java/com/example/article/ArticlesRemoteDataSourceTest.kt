package com.example.article

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.article.articles.data.models.Article
import com.example.article.articles.data.models.ArticlesResponse
import com.example.article.articles.data.remote.services.ArticlesService
import com.example.article.articles.data.remote.source.ArticleRemoteDataSource
import com.example.article.articles.data.remote.source.ArticleRemoteDataSourceImpl
import com.example.articles.MainCoroutineRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ArticlesRemoteDataSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var articleRemoteDataSource: ArticleRemoteDataSource


    private lateinit var articleService: ArticlesService


    private val fakeArticles = listOf(
        Article(
            "Beavers",
            "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony ",
            "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
        )
    )

    private val articlesResponse = ArticlesResponse("About Canada",fakeArticles)

    @Before
    fun init() {

        articleService = mock {
            onBlocking { getArticlesAsync() } doReturn GlobalScope.async {
                Response.success(articlesResponse)
            }
        }

        articleRemoteDataSource = ArticleRemoteDataSourceImpl(articleService, mainCoroutineRule.coroutineContext)


    }

    @Test
    fun testGetArticle() = runBlocking {

        articleService = mock {
            onBlocking { getArticlesAsync() } doReturn GlobalScope.async {
                Response.success(articlesResponse)
            }
        }

        articleRemoteDataSource = ArticleRemoteDataSourceImpl(articleService, mainCoroutineRule.coroutineContext)

        // Will be launched in the mainThreadSurrogate dispatcher
        val result = articleRemoteDataSource.getArticles()

        assert(result == articlesResponse)


    }

    @Test(expected = Exception::class)
    fun testThrowArticleException() = runBlocking {

        articleService = mock {
            onBlocking { getArticlesAsync() } doReturn GlobalScope.async {
                Response.error<ArticlesResponse>(404, null)
            }
        }

        articleRemoteDataSource = ArticleRemoteDataSourceImpl(articleService, mainCoroutineRule.coroutineContext)

        val result = articleRemoteDataSource.getArticles()




    }


}