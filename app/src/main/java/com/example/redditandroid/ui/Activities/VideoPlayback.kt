package com.example.redditandroid.ui.Activities

import android.content.Context
import android.content.MutableContextWrapper
import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

object VideoPlayback: Player.EventListener {

    var playbackPosition: Long = 0
    lateinit var simpleExoplayer: SimpleExoPlayer
    private lateinit var mContext:Context
    lateinit var dashURL:String





    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(mContext, "exoplayer-sample")
    }

    fun buildPlayer(mContext: Context):SimpleExoPlayer{
        this.mContext = mContext
        simpleExoplayer = SimpleExoPlayer.Builder(VideoPlayback.mContext).build()
        return simpleExoplayer
    }

    fun initializePlayer(){

        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)
    }

    fun startPlayback(){
        simpleExoplayer.prepare()
        simpleExoplayer.play()
    }



    fun releasePlayer(){
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }




    fun buildMediaSource(){
//        return if (type == "dash") {
//            DashMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(uri)
//        } else {
//            ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(uri)
//        }
        val mediaItem = MediaItem.fromUri(dashURL)
        simpleExoplayer.setMediaItem(mediaItem)
    }



}