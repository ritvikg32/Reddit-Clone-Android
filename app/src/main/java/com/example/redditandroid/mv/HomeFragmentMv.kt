package com.example.redditandroid.mv

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.redditandroid.api.ApiService
import com.example.redditandroid.api.ServiceBuilder
import com.example.redditandroid.api.UserAuthentication
import com.example.redditandroid.models.NavigationHeader
import com.example.redditandroid.models.User
import com.example.redditandroid.repository.HomeRepoObserver
import com.example.redditandroid.repository.RepoHomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.URL

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragmentMv(): ViewModel(), HomeRepoObserver, UserAuthentication.Authentication  {

    var navigationHead:MutableLiveData<NavigationHeader> = MutableLiveData()
    var imgBitmap:MutableLiveData<Bitmap> = MutableLiveData()

    var repository:RepoHomeActivity = RepoHomeActivity()
    var userAuth:UserAuthentication



    init {
        repository.registerObserver(this) //Registered this viewmodel for observer
        userAuth = UserAuthentication(this)
        scheduleRevokeToken()
    }


        val navigationData = liveData(Dispatchers.IO){
            val data = repository.getNavigationData()
            emit(data)
        }

    fun navigationTest(){
        repository.getNavigationData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleRevokeToken(){
        //Scheduling revoke thread
        UserAuthentication.isScheduleActive=true
        userAuth.revokeToken()
    }








    override suspend fun onCredentialsChanged(navObject: NavigationHeader?) {
        navigationHead.postValue(navObject)
    }

    override suspend fun onUserImgAvailable(imgBitmap: Bitmap?) {
        this.imgBitmap.postValue(imgBitmap)
    }

    override fun onUserAuthenticated() {
        UserAuthentication.isUserAuthenticated = true
        navigationTest()
    }


}