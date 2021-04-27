package com.example.redditandroid.models

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

data class NavigationLvModel (var label:String, var icon:Drawable)

data class NavigationSubreddit(val display_name_prefixed:String)
data class NavigationHeader(val total_karma:Int, val coins:Int,val icon_img:String, val subreddit: NavigationSubreddit, val imgBitmap: Bitmap?)