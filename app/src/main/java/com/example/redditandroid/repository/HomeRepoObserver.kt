package com.example.redditandroid.repository

import android.graphics.Bitmap
import com.example.redditandroid.models.NavigationHeader
import com.example.redditandroid.models.User

interface HomeRepoObserver {
    suspend fun onCredentialsChanged(navObject: NavigationHeader?)
    suspend fun onUserImgAvailable(imgBitmap: Bitmap?)
}