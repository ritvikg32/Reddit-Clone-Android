package com.example.redditandroid.repository

import com.example.redditandroid.models.*

interface TabHomeObserver {
    suspend fun PostsFetched(postList:ArrayList<PostData1Children>)
    suspend fun SubredditInfoFetched(subredditMineListing: SubredditMineListing)
    suspend fun OnSearchResultFetched(search : SearchSR)
}