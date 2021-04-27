package com.example.redditandroid.repository

import android.util.Log
import com.example.redditandroid.api.ApiService
import com.example.redditandroid.api.ServiceBuilder
import com.example.redditandroid.models.PostData
import com.example.redditandroid.models.PostDataParent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class TabHomeRepository{

    var mObservers:ArrayList<TabHomeObserver> = ArrayList()
    private val apiService: ApiService = ServiceBuilder.buildServiceToken(ApiService::class.java)



    fun registerObserver(observer:TabHomeObserver){
        if(observer !in mObservers)
            mObservers.add(observer)
    }

    fun getBestPost(){
        Log.d("post","repo function gets called")
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO){
                val response: Response<PostDataParent> = apiService.getPostListing()

                if(response.isSuccessful && response.body()!=null){
                    for(obs in mObservers)
                        obs.PostsFetched(response?.body()!!.data.children)
                    Log.d("response","Posts received!")
                    Log.d("response post",response?.body()!!.data.children.toString())
                }
                else
                    Log.d("Respone","Posts could not be fetched")
            }
        }
    }



}