package com.example.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.article.TestUtils
import com.example.article.articles.data.models.ArticlesResponse
import com.example.article.articles.domain.GetArticlesUseCase
import com.example.article.articles.presentation.viewmodel.ArticlesViewModel
import com.example.article.common.Result
import com.example.article.common.Status
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ArticlesViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    lateinit var articleViewModel: ArticlesViewModel

    lateinit var getArticlesUseCase: GetArticlesUseCase

    private val articlesResponse = TestUtils().articlesResponse


    @Test
    fun testLoadingData() = mainCoroutineRule.runBlockingTest {

        getArticlesUseCase = mock {
            onBlocking { getArticles() } doReturn object : LiveData<Result<ArticlesResponse>>() {
                init {
                    value = Result.loading()
                }
            }
        }


        articleViewModel = ArticlesViewModel(getArticlesUseCase)

        val result = articleViewModel.articleResult

        result.observeForever {}


        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(articleViewModel.articleResult).status == Status.LOADING)

    }


    @Test
    fun testSuccessData() = mainCoroutineRule.runBlockingTest {

        getArticlesUseCase = mock {
            onBlocking { getArticles() } doReturn object : LiveData<Result<ArticlesResponse>>() {
                init {
                    value = Result.success(articlesResponse)
                }
            }
        }

        articleViewModel = ArticlesViewModel(getArticlesUseCase)

        val result = articleViewModel.articleResult

        result.observeForever {}

        kotlinx.coroutines.delay(2000)


        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS &&
                LiveDataTestUtil.getValue(result).data == articlesResponse)

    }


    @Test
    fun testErrorData() = mainCoroutineRule.runBlockingTest {

        getArticlesUseCase = mock {
            onBlocking { getArticles() } doReturn object : LiveData<Result<ArticlesResponse>>() {
                init {
                    value = Result.error("error")
                }
            }
        }

        articleViewModel = ArticlesViewModel(getArticlesUseCase)

        val result = articleViewModel.articleResult

        result.observeForever {}

        kotlinx.coroutines.delay(2000)


        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR &&
                LiveDataTestUtil.getValue(result).message == "error")

    }


}