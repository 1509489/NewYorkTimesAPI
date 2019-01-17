package com.pixelart.newyorktimesapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.pixelart.newyorktimesapi.data.model.*
import com.pixelart.newyorktimesapi.data.network.NetworkService
import com.pixelart.newyorktimesapi.data.repository.ArticleDataSource
import com.pixelart.newyorktimesapi.factories.DataSourceFactory
import com.pixelart.newyorktimesapi.ui.homescreen.HomePagingViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor
import kotlin.collections.ArrayList

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    private lateinit var pagedViewModel: HomePagingViewModel
    private lateinit var apiResponse: APIResponse

    @Mock private lateinit var networkService: NetworkService
    @Mock lateinit var observer: Observer<PagedList<Doc>>
    @Mock lateinit var stateObserver: Observer<ArticleDataSource.State>
    
    @Captor
    lateinit var captor: ArgumentCaptor<PagedList<Doc>>
    
    @Captor lateinit var stateCaptor: ArgumentCaptor<ArticleDataSource.State>
    
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    

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
    
    /*private val immediateScheduler = object : Scheduler() {
        override fun scheduleDirect(
            run: Runnable,
            delay: Long, unit: TimeUnit
        ): Disposable {
            return super.scheduleDirect(run, 0, unit)
        }
        
        override fun createWorker(): Scheduler.Worker {
            return ExecutorScheduler.ExecutorWorker(
                Executor { it.run() })
        }
    }*/
    
    @Before
    fun setup(){
//        RxJavaPlugins.setInitIoSchedulerHandler { immediateScheduler }
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }
        MockitoAnnotations.initMocks(this)
        
        val dataSourceFactory = DataSourceFactory(networkService)
        pagedViewModel = HomePagingViewModel(dataSourceFactory)

        val meta = Meta(10, 0, 43)
        val doc = Doc(headline = Headline(), snippet = "Test snippet")
        val docList = ArrayList<Doc>()
        
        for (i in 0 until 10){
            docList.add(doc)
        }
        
        val response = Response(docList, meta)
        apiResponse = APIResponse("", "", response)
    }

    @Test
    fun testSuccess(){
        Mockito.`when`(networkService.getArticles(Mockito.anyString(), Mockito.anyString(),
            Mockito.anyInt(), Mockito.anyString())).thenReturn(Single.just(apiResponse))
        
        pagedViewModel.getArticles().observeForever(observer)
        
        Mockito.verify(observer).onChanged(captor.capture())
        //Assert.assertEquals(10, captor.value.size)
        Assert.assertTrue(captor.value.size == 10)
        val doc = captor.value[0]
        Assert.assertEquals("Test snippet", doc?.snippet)
    }
    
    @Test
    fun testFailure(){
        Mockito.`when`(networkService.getArticles(Mockito.anyString(), Mockito.anyString(),
            Mockito.anyInt(), Mockito.anyString())).thenReturn(Single.error(Exception("Error")))
        pagedViewModel.getArticles().observeForever(observer)
        pagedViewModel.getState().observeForever(stateObserver)
        
        Mockito.verify(stateObserver).onChanged(stateCaptor.capture())
        Assert.assertEquals(ArticleDataSource.State.FAILURE, stateCaptor.value)
    }
}