package com.example.redditandroid.models



data class User(val username: String?, val reddit_coins:Int?, val display_picture:String?, val date_joined:String?, val karma:Int?)

data class UserRegistrationModel(val response: String, val email:String, val username:String, val token:String)

data class UserValidatonModel(val isAvailable:Boolean)

data class RevokeTokenModel(val access_token:String, val token_type:String, val expires_in:String, val scope:String)