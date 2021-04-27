package com.example.redditandroid.api

import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "https://oauth.reddit.com/"
//    private const val BASE_URL = "http:/10.0.2:8000/"
     var authToken:String? = "590034730001-W7fFu5PGRAw1tDw92sDb9qU92aycGQ"


    private const val REDDIT_BASE_URL = "https://www.reddit.com/"
    private const val basicHTTPHeader = "Basic Ym9yOEU5cTY1MHUwZ2c6"

//    private val okHttp = OkHttpClient.Builder()
//
//    private val builder:Retrofit.Builder = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
//        .client(okHttp.build())
//
//    private val retrofitInstance:Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T{

        val okHttp = OkHttpClient.Builder()
        val builder:Retrofit.Builder = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())

        val retrofitInstance:Retrofit = builder.build()
        return retrofitInstance.create(serviceType)
    }

    fun <T> buildRevokeService(serviceType: Class<T>):T{

        val okHttpBuilder = OkHttpClient.Builder()

        okHttpBuilder.addInterceptor(object :Interceptor{
            override fun intercept(chain: Interceptor.Chain?): Response {
                val request: Request? = chain?.request()

                Log.d("Request","Token is $authToken")
                val newRequest:Request.Builder? = request?.newBuilder()?.addHeader("Authorization",
                    basicHTTPHeader)

                return chain?.proceed(newRequest?.build())!!



            }

        })

        val builder:Retrofit.Builder = Retrofit.Builder().baseUrl(REDDIT_BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())

        val retrofitInstance:Retrofit = builder.build()

        return retrofitInstance.create(serviceType)
    }

    fun <T> buildServiceToken(serviceType: Class<T>):T{

        val okHttpBuilder = OkHttpClient.Builder()

        okHttpBuilder.addInterceptor(object :Interceptor{
            override fun intercept(chain: Interceptor.Chain?): Response {
                val request: Request? = chain?.request()

                val newRequest:Request.Builder? = request?.newBuilder()?.addHeader("Authorization","bearer $authToken")

                return chain?.proceed(newRequest?.build())!!



            }

        })

         val builder:Retrofit.Builder = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())

         val retrofitInstance:Retrofit = builder.build()

        return retrofitInstance.create(serviceType)
    }


}