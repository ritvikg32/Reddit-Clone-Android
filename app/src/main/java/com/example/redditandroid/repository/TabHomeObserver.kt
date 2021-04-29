package com.example.redditandroid.repository

import com.example.redditandroid.models.PostData
import com.example.redditandroid.models.PostData1Children
import com.example.redditandroid.models.SubredditMineListing
import com.example.redditandroid.models.SubredditParent

interface TabHomeObserver {
    suspend fun PostsFetched(postList:ArrayList<PostData1Children>)
    suspend fun SubredditInfoFetched(subredditMineListing: SubredditMineListing)
}