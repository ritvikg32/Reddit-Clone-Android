package com.example.redditandroid.repository

import android.util.Log
import com.example.redditandroid.api.ApiService
import com.example.redditandroid.api.ServiceBuilder
import com.example.redditandroid.models.*
import com.google.gson.JsonElement
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
                    Log.d("Response","Posts could not be fetched")
            }
        }
    }

    fun getSubredditInfo(subName:String){
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                val response:Response<SubredditParent> = apiService.getSubredditInfo(subName)

                if(response.isSuccessful && response.body()!=null){
                    for(obs in mObservers){
//                        dfsf
                    }
                }
                else
                    Log.d("Response","Error fetching subreddit info")
            }
        }
    }

    fun getSubscribedSubreddits(){
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                val response:Response<SubredditMineListingParent> = apiService.getSubredditSubscriber("subscriber")

                if(response.isSuccessful && response.body()!=null){

                    for(obs in mObservers){
                        obs.SubredditInfoFetched(response?.body()!!.data)
                    }
                }
                else
                    Log.d("Response","Error fetching subreddit info ${response.errorBody()}")
            }
        }
    }

    fun castVote(id:String, dir:Int){
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                val response:Response<JsonElement> = apiService.castVote(id = id,dir = dir)

                if(response.isSuccessful && response.body()==null){
                    Log.d("Response","Vote casted successfully")
                }
                else
                    Log.d("response","couldn't cast vote")
            }
        }
    }



}