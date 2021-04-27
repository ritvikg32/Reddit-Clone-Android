package com.example.redditandroid.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class CoroutineWork{

    companion object{
        fun getImageFromURL(url:String, mContext:Any){
            val crtInterface:CoroutineResponse = mContext as CoroutineResponse
            GlobalScope.launch(Dispatchers.IO) {
                var image: Bitmap? = null
                try {
                    val url = URL(url)
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    crtInterface.OnBitmapReceived(image)
                }
                catch (e: IOException){
                    println("Could fetch image from url")
                }






            }
        }
    }



    interface CoroutineResponse{
        suspend fun OnBitmapReceived(bitmapImg:Bitmap?)
    }

}