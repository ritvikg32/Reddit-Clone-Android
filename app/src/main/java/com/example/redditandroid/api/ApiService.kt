package com.example.redditandroid.api

import com.example.redditandroid.models.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //User Authentication
//
//    @POST("api/account/login")
//    fun userLogin(@Body data:Map<String, String>): Call<User>

//    @JvmSuppressWildcards
//    @Multipart
//    @POST(value = "api/account/register")
//    fun userRegistrationDp(@PartMap() data: Map<String, RequestBody>, @Part file: MultipartBody.Part):Call<UserRegistrationModel>
//
//
//
//    @POST(value = "api/account/register")
//    fun userRegistration(@Body data: Map<String, String>):Call<UserRegistrationModel>


//    @HTTP(path = "api/account/username_validation", method = "POST", hasBody = true)
//    fun userValidation(@Body data: Map<String, String>):Call<UserValidatonModel>

    @FormUrlEncoded
    @POST(value = "api/v1/access_token")
    fun revokeToken(@Field("grant_type") grant_type:String, @Field("refresh_token") refresh_token:String):Call<RevokeTokenModel>


    @GET(value="api/v1/me")
    suspend fun getUserCredentials():Response<NavigationHeader>


    @GET(value="best?limit=15")
    suspend fun getPostListing():Response<PostDataParent>

    @GET(value="r/{subreddit_name}/about")
    suspend fun getSubredditInfo(@Path("subreddit_name")subredditName:String):Response<SubredditParent>








}