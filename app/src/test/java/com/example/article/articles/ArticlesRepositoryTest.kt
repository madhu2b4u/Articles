package com.example.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.article.TestUtils
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

    private val articlesResponse = TestUtils().articlesResponse


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
