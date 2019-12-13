package com.example.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.article.articles.data.models.Article
import com.example.article.articles.data.models.ArticlesResponse
import com.example.article.articles.data.repository.ArticleRepository
import com.example.article.articles.domain.GetArticlesUseCase
import com.example.article.articles.domain.GetArticlesUseCaseImpl
import com.example.article.common.Status
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import com.example.article.common.Result

@ExperimentalCoroutinesApi
class GetArticlesUseCaseTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var getArticlesUseCase: GetArticlesUseCase

    private lateinit var articleRepository: ArticleRepository

    private val fakeArticles = listOf(
        Article(
            "Beavers",
            "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony ",
            "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
        )
    )

    private val articlesResponse = ArticlesResponse("About Canada",fakeArticles)




    @Test
    fun testLoadingData()=mainCoroutineRule.runBlockingTest{
            articleRepository = mock {
                onBlocking { getArticles() } doReturn object : LiveData<Result<ArticlesResponse>>() {
                    init {
                        value = Result.loading()
                    }
                }
            }
            getArticlesUseCase = GetArticlesUseCaseImpl(articleRepository)

            val result = getArticlesUseCase.getArticles()

            result.observeForever {  }

            assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)

        }


    @Test
    fun testSuccessData()=mainCoroutineRule.runBlockingTest{
        articleRepository = mock {
            onBlocking { getArticles() } doReturn object : LiveData<Result<ArticlesResponse>>() {
                init {
                    value = Result.success(articlesResponse)
                }
            }
        }
        getArticlesUseCase = GetArticlesUseCaseImpl(articleRepository)

        val result = getArticlesUseCase.getArticles()

        result.observeForever {  }

        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS && LiveDataTestUtil.getValue(result).data == articlesResponse)

    }

    @Test
    fun testErrorData()=mainCoroutineRule.runBlockingTest{
        articleRepository = mock {
            onBlocking { getArticles() } doReturn object : LiveData<Result<ArticlesResponse>>() {
                init {
                    value = Result.error("error")
                }
            }
        }
        getArticlesUseCase = GetArticlesUseCaseImpl(articleRepository)

        val result = getArticlesUseCase.getArticles()

        result.observeForever {  }

        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR && LiveDataTestUtil.getValue(result).message == "error")

    }


}
