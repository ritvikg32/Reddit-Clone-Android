package com.example.redditandroid.mv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redditandroid.api.UserAuthentication
import com.example.redditandroid.models.*
import com.example.redditandroid.repository.TabHomeObserver
import com.example.redditandroid.repository.TabHomeRepository
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

class TabHomeViewModel: ViewModel(), TabHomeObserver{

    //Live Data
    var postList:MutableLiveData<ArrayList<PostData1Children>> = MutableLiveData()
    var subscribedSubredditList:MutableLiveData<SubredditMineListing> = MutableLiveData()
    var searchData:MutableLiveData<SearchSR> = MutableLiveData()



    //Repository
    var repository:TabHomeRepository = TabHomeRepository()
    var noOfPosts:Int = 15



    private lateinit var exoPlayer:SimpleExoPlayer
    private var playbackPosition: Long = 0
    private lateinit var videoURL:String



    init {
        repository.registerObserver(this)
    }

    fun getPosts(forceUpdate:Boolean, postFilter:String){
        if(postList.value==null || forceUpdate)
            repository.getPosts(filterType = postFilter, limit = noOfPosts)
    }

    fun getSubscribedSubreddits(){
        if(subscribedSubredditList.value==null)
            repository.getSubscribedSubreddits()
    }

    fun castVote(id:String, dir:Int){
        repository.castVote(id, dir)
    }

    fun searchSubreddit(query:String){
        repository.searchSubreddit(query, true)
    }


    override suspend fun PostsFetched(postList: ArrayList<PostData1Children>) {
        this.postList.postValue(postList)
    }

    override suspend fun SubredditInfoFetched(subredditMineListing: SubredditMineListing) {
        this.subscribedSubredditList.postValue(subredditMineListing)
    }

    override suspend fun OnSearchResultFetched(search: SearchSR) {
        this.searchData.postValue(search)
    }


}





