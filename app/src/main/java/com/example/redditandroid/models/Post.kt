package com.example.redditandroid.models


data class RedditVideo (

    val bitrate_kbps : Int,
    val fallback_url : String,
    val height : Int,
    val width : Int,
    val scrubber_media_url : String,
    val dash_url : String,
    val duration : Int,
    val hls_url : String,
    val is_gif : Boolean,
    val transcoding_status : String
)


data class PostData(
    val author:String, val saved:Boolean, val title:String, val subreddit_name_prefixed:String,
    val thumbnail_height:Int, val total_awards_received:String, val score:Int,
    val thumbnail:String, val id:String, val num_comments:Int, val is_video:Boolean, val media:Media?, val over_18:Boolean,
    val createdUTC:Double, val url:String, val all_awardings:ArrayList<Award>, val likes:Boolean?
)

data class Media(
    val reddit_video : RedditVideo
)
data class Award(
    val resized_icons:ArrayList<ResizedAward>
)

data class ResizedAward(
    val url:String,
    val width:String,
    val height:String
)

data class PostData1Children(
    val kind:String, val data:PostData
)

data class PostData1(
    val dist:Int, val children: ArrayList<PostData1Children>
)

data class PostDataParent(val kind:String, val data:PostData1)

