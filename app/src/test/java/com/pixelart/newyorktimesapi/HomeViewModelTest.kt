package com.pixelart.newyorktimesapi

import com.pixelart.newyorktimesapi.data.model.APIResponse
import com.pixelart.newyorktimesapi.data.model.Meta
import com.pixelart.newyorktimesapi.data.model.Response
import com.pixelart.newyorktimesapi.data.network.NetworkService
import com.pixelart.newyorktimesapi.factories.DataSourceFactory
import com.pixelart.newyorktimesapi.ui.homescreen.HomePagingViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import java.util.concurrent.Executor

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    private lateinit var pagedViewModel: HomePagingViewModel
    private lateinit var apiResponse: APIResponse

    @Mock private lateinit var networkService: NetworkService

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupSchedulers(){
            val scheduler = object : Scheduler(){
                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }
            RxJavaPlugins.setInitIoSchedulerHandler { scheduler }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
        }
    }

    @Before
    fun setup(){
        val dataSourceFactory = DataSourceFactory(networkService)
        pagedViewModel = HomePagingViewModel(dataSourceFactory)

        val meta = Meta(10, 0, 43)
        val response = Response(Collections.emptyList(), meta)
        apiResponse = APIResponse("", "", response)
    }

    @Test
    fun testSuccess(){
        Mockito.`when`(networkService.getArticles(Mockito.anyString(), Mockito.anyString(),
            Mockito.anyInt(), Mockito.anyString())).thenReturn(Single.just(apiResponse))

    }
}