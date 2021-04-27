package com.example.redditandroid.api

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.redditandroid.R
import com.example.redditandroid.models.RevokeTokenModel
import com.example.redditandroid.models.User
import com.example.redditandroid.models.UserRegistrationModel
import com.example.redditandroid.models.UserValidatonModel
import com.example.redditandroid.ui.Activities.HomeActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.time.Duration

class UserAuthentication(observer:Authentication) {

    var mContext:Context? = null
    val REFRESH_TOKEN = "590034730001-tLHhdEuiMnbFZtBYIx8Fnpj_bQeFrQ"
    var authenticationObservers:ArrayList<Authentication> = ArrayList()

    init {
        registerObserver(observer)
    }


    companion object{
        var  isScheduleActive:Boolean = false
        var isUserAuthenticated = false
    }

    private fun registerObserver(observer:Authentication){
        if(observer !in authenticationObservers)
            authenticationObservers.add(observer)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun revokeToken(){
        GlobalScope.launch {

            while(isScheduleActive){

                val apiService:ApiService = ServiceBuilder.buildRevokeService(ApiService::class.java)


                val requestCall: Call<RevokeTokenModel> = apiService.revokeToken("refresh_token", REFRESH_TOKEN)

                requestCall.enqueue(object : Callback<RevokeTokenModel> {
                    override fun onResponse(
                        call: Call<RevokeTokenModel>?,
                        response: Response<RevokeTokenModel>?
                    ){
                        if(response!=null && response.isSuccessful){
                            Log.i("TOKEN","Token revoked successfully")
                            ServiceBuilder.authToken = response?.body()?.access_token
                            Log.d("Token","Access token is ${response?.body()}")
                            authenticated()
                        }
                        else
                            Log.d("token", "something went wrong")

                    }

                    override fun onFailure(call: Call<RevokeTokenModel>?, t: Throwable?) {
                        Log.d("FAILURE", t.toString())
                    }


                })

                delay(2400000) //Every 40 mins
            }


        }
    }

    fun authenticated(){
            for(obs in authenticationObservers)
                obs.onUserAuthenticated()
    }

    interface Authentication{
        fun onUserAuthenticated()
    }




//    fun serverUserValidation(username: String, email: String, password: String, dpUri: Uri?){
//
//        val apiService:ApiService = ServiceBuilder.buildService(ApiService::class.java)
//
//        var formData = mapOf<String, String>("email" to email, "username" to username)
//
//        val requestCall: Call<UserValidatonModel> = apiService.userValidation(formData)
//
//
//
//        requestCall.enqueue(object : Callback<UserValidatonModel> {
//            override fun onFailure(call: Call<UserValidatonModel>?, t: Throwable?) {
//                Log.d("FAILURE", t.toString())
//            }
//
//            override fun onResponse(
//                call: Call<UserValidatonModel>?, response: Response<UserValidatonModel>?
//            ) {
//                if (response!!.isSuccessful) {
//                    if (response.body().isAvailable) {
//                        Toast.makeText(
//                            mContext,
//                            "username and email are available",
//                            Toast.LENGTH_LONG
//                        ).show()
//                        if (dpUri == null)
//                            registerUser(email, username, password)
//                        else
//                            registerUser(email, username, password, image_uri = dpUri)
//                    } else
//                        Toast.makeText(
//                            mContext,
//                            "username or email already in use",
//                            Toast.LENGTH_LONG
//                        ).show()
//                } else {
//                    Toast.makeText(mContext, "Oops! something went wrong", Toast.LENGTH_LONG).show()
//                }
//            }
//
//        })
//
//
//
//    }
//
//    fun registerUser(email: String, username: String, password: String){
//        val apiService:ApiService = ServiceBuilder.buildService(ApiService::class.java)
//
//        var formData = mapOf<String, String>(
//            "email" to email,
//            "username" to username,
//            "password" to password
//        )
//
//        val requestCall: Call<UserRegistrationModel> = apiService.userRegistration(formData)
//
//        requestCall.enqueue(object : Callback<UserRegistrationModel> {
//            override fun onResponse(
//                call: Call<UserRegistrationModel>?,
//                response: Response<UserRegistrationModel>?
//            ) {
//                if (response?.isSuccessful!!) {
//                    Toast.makeText(mContext, "User registered successfully", Toast.LENGTH_LONG)
//                        .show()
//                    login(email,password)
//                } else
//                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show()
//
//                Log.d("Response", response.body().toString())
//            }
//
//            override fun onFailure(call: Call<UserRegistrationModel>?, t: Throwable?) {
//                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show()
//                Log.d("LoginActivity", t.toString())
//            }
//
//
//        })
//    }
//
//    fun registerUser(email: String, username: String, password: String, image_uri: Uri){
//        val apiService:ApiService = ServiceBuilder.buildService(ApiService::class.java)
//
//        var formData = mapOf<String, RequestBody>(
//            "email" to toRequestBody(email),
//            "username" to toRequestBody(username),
//            "password" to toRequestBody(password)
//        )
//
//
//        Log.d("Authentication", "Entered registerUser()")
//
//
//
//        val requestCall: Call<UserRegistrationModel> = apiService.userRegistrationDp(
//            formData, FileUpload().uploadFile(
//                image_uri,
//                mContext
//            )
//        )
//
//        requestCall.enqueue(object : Callback<UserRegistrationModel> {
//            override fun onResponse(
//                call: Call<UserRegistrationModel>?,
//                response: Response<UserRegistrationModel>?
//            ) {
//                if (response?.isSuccessful!!) {
//                    Toast.makeText(mContext, "User registered successfully (DP)", Toast.LENGTH_LONG)
//                        .show()
//                } else
//                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show()
//
//                Log.d("Response", response.body().toString())
//            }
//
//            override fun onFailure(call: Call<UserRegistrationModel>?, t: Throwable?) {
//                Toast.makeText(
//                    mContext,
//                    mContext?.resources?.getString(R.string.error_txt),
//                    Toast.LENGTH_LONG
//                ).show()
//                Log.d("LoginActivity", t.toString())
//            }
//
//
//        })
//    }
//
//    fun login(email: String, password: String){
//        val apiService:ApiService = ServiceBuilder.buildService(ApiService::class.java)
//
//        var formaData = mapOf<String, String>("username" to email, "password" to password)
//
//        val requestCall: Call<User> = apiService.userLogin(formaData)
//
//        requestCall.enqueue(object : Callback<User> {
//            override fun onResponse(call: Call<User>?, response: Response<User>?) {
//                Toast.makeText(mContext, "Authentication successful", Toast.LENGTH_LONG).show()
//                if (response != null) {
//                    if (response.isSuccessful && response.body() != null) {
//                        Log.d("Response", response.body().toString())
//                        ServiceBuilder.authToken = response.body().token
//                        saveToken(response.body().token)
//                        val intent = Intent(mContext, HomeActivity::class.java)
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        mContext?.startActivity(intent)
//                    } else
//                        Toast.makeText(
//                            mContext,
//                            mContext?.resources?.getString(R.string.wrong_credentials),
//                            Toast.LENGTH_LONG
//                        ).show()
//                }
//
//            }
//
//            override fun onFailure(call: Call<User>?, t: Throwable?) {
//                Toast.makeText(
//                    mContext,
//                    mContext?.resources?.getString(R.string.error_txt),
//                    Toast.LENGTH_LONG
//                ).show()
//                Log.d("LoginActivity", t.toString())
//            }
//
//        })
//    }

    fun saveToken(token: String){
        val sharedPref = mContext?.getSharedPreferences("Token", Context.MODE_PRIVATE)

        if(sharedPref?.getString(mContext?.resources?.getString(R.string.sp_token_key),null) == null){
            with(sharedPref?.edit()){
                this?.putString("TOKEN", token)
                this?.apply()
            }
            Log.d("sp","Token saved!")
        }
        Log.d("sp","Token was already present")
    }

    // This method  converts String to RequestBody
    fun toRequestBody(value: String?): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    }


    fun getToken():String?{
        val sharedPref = mContext?.getSharedPreferences("Token",Context.MODE_PRIVATE)

        return sharedPref?.getString("TOKEN",null)
    }
}