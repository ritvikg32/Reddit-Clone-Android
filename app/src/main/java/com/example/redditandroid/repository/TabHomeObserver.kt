package com.example.redditandroid.repository

import com.example.redditandroid.models.PostData
import com.example.redditandroid.models.PostData1Children

interface TabHomeObserver {
    suspend fun PostsFetched(postList:ArrayList<PostData1Children>)
}