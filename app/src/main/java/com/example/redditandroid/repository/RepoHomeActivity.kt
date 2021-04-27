package com.example.redditandroid.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.appcompat.widget.WithHint
import com.bumptech.glide.Glide
import com.example.redditandroid.api.ApiService
import com.example.redditandroid.api.ServiceBuilder
import com.example.redditandroid.models.NavigationHeader
import com.example.redditandroid.models.User
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.URL

class RepoHomeActivity:CoroutineWork.CoroutineResponse {

    private val apiService:ApiService = ServiceBuilder.buildServiceToken(ApiService::class.java)

    private var mObservers = ArrayList<HomeRepoObserver>()

    private val instance = this


    fun registerObserver(repoInstance:HomeRepoObserver){
        if(!mObservers.contains(repoInstance))
            mObservers.add(repoInstance)
    }

    fun getNavigationData(){

        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO){
                val response:Response<NavigationHeader> = apiService.getUserCredentials()


                if(response.isSuccessful && response.body()!=null){
                    for(obs in mObservers)
                        obs.onCredentialsChanged(response.body())
                    CoroutineWork.getImageFromURL(response.body()!!.icon_img,instance)
                    Log.d("response",response.body().toString())
                }
            }
        }





    }



    override suspend fun OnBitmapReceived(bitmapImg: Bitmap?) {
        for(obs in mObservers){
            obs.onUserImgAvailable(bitmapImg)
        }
    }


}