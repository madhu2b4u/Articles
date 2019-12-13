package com.example.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.article.articles.data.models.Article
import com.example.article.articles.data.models.ArticlesResponse
import com.example.article.articles.data.remote.source.ArticleRemoteDataSource
import com.example.article.articles.data.repository.ArticleRepository
import com.example.article.articles.data.repository.ArticleRepositoryImpl
import com.example.article.common.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ArticlesRepositoryTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: ArticleRepository

    @Mock
    lateinit var remoteDataSource: ArticleRemoteDataSource


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
        MockitoAnnotations.initMocks(this)

        repository = ArticleRepositoryImpl(remoteDataSource)


    }

    @Test
    fun testGetArticlesFromAPI() = mainCoroutineRule.runBlockingTest {

        Mockito.`when`(remoteDataSource.getArticles()).thenReturn(articlesResponse)

        val result = repository.getArticles()

        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)

        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
        assert(LiveDataTestUtil.getValue(result).data == articlesResponse)

    }

    @Test(expected = Exception::class)
    fun testGetArticlesThrowException() = mainCoroutineRule.runBlockingTest {

        Mockito.doThrow(Exception("error")).`when`(remoteDataSource.getArticles())
        repository.getArticles()


    }


}
