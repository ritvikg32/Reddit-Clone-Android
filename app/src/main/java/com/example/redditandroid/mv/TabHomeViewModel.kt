package com.example.redditandroid.mv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redditandroid.api.UserAuthentication
import com.example.redditandroid.models.PostData
import com.example.redditandroid.models.PostData1Children
import com.example.redditandroid.models.Subreddit
import com.example.redditandroid.models.SubredditMineListing
import com.example.redditandroid.repository.TabHomeObserver
import com.example.redditandroid.repository.TabHomeRepository
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

class TabHomeViewModel: ViewModel(), TabHomeObserver{

    //Live Data
    var postList:MutableLiveData<ArrayList<PostData1Children>> = MutableLiveData()
    var subscribedSubredditList:MutableLiveData<SubredditMineListing> = MutableLiveData()



    //Repository
    var repository:TabHomeRepository = TabHomeRepository()



    private lateinit var exoPlayer:SimpleExoPlayer
    private var playbackPosition: Long = 0
    private lateinit var videoURL:String



    init {
        repository.registerObserver(this)
    }

    fun getBestPost(){
        if(postList.value==null)
            repository.getBestPost()
    }

    fun getSubscribedSubreddits(){
        if(subscribedSubredditList.value==null)
            repository.getSubscribedSubreddits()
    }


    override suspend fun PostsFetched(postList: ArrayList<PostData1Children>) {
        this.postList.postValue(postList)
    }

    override suspend fun SubredditInfoFetched(subredditMineListing: SubredditMineListing) {
        this.subscribedSubredditList.postValue(subredditMineListing)
    }


}





