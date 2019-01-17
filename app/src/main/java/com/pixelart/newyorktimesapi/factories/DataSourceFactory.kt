package com.pixelart.newyorktimesapi.factories

//import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.network.NetworkService
import com.pixelart.newyorktimesapi.data.repository.ArticleDataSource

class DataSourceFactory(private val networkService: NetworkService): DataSource.Factory<Int, Doc>() {
    //private val TAG = "DataSourceFactory"

    private val docLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Doc>>()
    private lateinit var dataSource: ArticleDataSource
    private var query: String = ""
    private var queryFilter: String = ""
    private val stateObservable = MutableLiveData<ArticleDataSource.State>()

    override fun create(): DataSource<Int, Doc> {
        dataSource = ArticleDataSource(networkService, query, queryFilter, 0, stateObservable)
        docLiveDataSource.postValue(dataSource)
        
        return dataSource
    }

    fun getDocLiveDataSource(): LiveData<PageKeyedDataSource<Int, Doc>> = docLiveDataSource
    
    fun getStateObservable(): LiveData<ArticleDataSource.State> = stateObservable
    

    fun query(query: String){
       if(::dataSource.isInitialized){
           dataSource.setQuery()
       }
        this.query = query
    }

    fun queryFilter(queryFilter: String){
        if (::dataSource.isInitialized){
            dataSource.setFilter()
        }
        this.queryFilter = queryFilter
    }
}