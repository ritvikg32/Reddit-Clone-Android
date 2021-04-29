package com.example.redditandroid.ui.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.redditandroid.R
import com.example.redditandroid.models.PostData
import com.example.redditandroid.models.PostData1Children
import com.example.redditandroid.models.SubredditParentMine
import com.example.redditandroid.ui.Activities.VideoPlayback
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.common.hash.HashingOutputStream


class PostRvAdapter(mContext:Context, requestManager: RequestManager):RecyclerView.Adapter<PostRvAdapter.myViewHolder>(), Player.EventListener {

    var postList:ArrayList<PostData1Children> = ArrayList()
    var subredditMineListingChildren:ArrayList<SubredditParentMine> = ArrayList<SubredditParentMine>()

    var mContext: Context
    lateinit var currentHolder:myViewHolder
    val videoPlaybackService:VideoPlayback = VideoPlayback
    var requestManager:RequestManager
    lateinit var simpleExoPlayer:SimpleExoPlayer

    lateinit var adapter:AwardListAdapter

    init {
        this.mContext = mContext
        this.requestManager = requestManager
//        videoPlaybackService.buildPlayer(mContext)
    }





    class myViewHolder(view:View, requestManager: RequestManager) : RecyclerView.ViewHolder(view){
        var postedByText:TextView
        var subredditName:TextView
        var postedTime:TextView
        var subredditDp:ImageView
        var voteNo:TextView
        var commentNo:TextView
        var postTitle:TextView
        var postImg:ImageView
        var awardRv:RecyclerView
        var videoProgressBar:ProgressBar
        var exoPlayerView:PlayerView
        var volumeControl:ImageView
        var requestManager:RequestManager
        var exoFrameLayout:FrameLayout

        init {
            postedByText = view.findViewById(R.id.rv_posted_by)
            subredditName = view.findViewById(R.id.rv_community_name)
            postedTime = view.findViewById(R.id.rv_posted_by)
            subredditDp  = view.findViewById(R.id.rv_community_dp)
            voteNo = view.findViewById(R.id.rv_upvote_no)
            commentNo = view.findViewById(R.id.rv_comment_no)
            postTitle = view.findViewById(R.id.post_title)
            postImg = view.findViewById(R.id.post_img)
            awardRv = view.findViewById(R.id.award_rv)
            videoProgressBar = view.findViewById(R.id.video_pb)
            exoPlayerView = view.findViewById(R.id.home_tab_vp)
            volumeControl = view.findViewById(R.id.volume_control)
            this.requestManager = requestManager
            exoFrameLayout = view.findViewById(R.id.exo_frame_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.hometab_rv_item, parent, false)

        return myViewHolder(view,this.requestManager)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val theItem:PostData = postList.get(position).data
        currentHolder = holder
        holder.postedByText.text = "Posted by "+theItem.author
        holder.postedTime
        holder.subredditName.text = theItem.subreddit_name_prefixed

        holder.voteNo.text =  getVoteNofromInt(theItem.score)
        holder.commentNo.text = theItem.num_comments.toString()
        holder.postTitle.text = theItem.title.toString()

        simpleExoPlayer = VideoPlayback.buildPlayer(mContext)



        if(!theItem.is_video) {
            setImage(theItem, holder.postImg)
            holder.exoPlayerView.visibility = View.INVISIBLE
            holder.postImg.visibility = View.VISIBLE
        }
        else{
            if(theItem.media!=null) {
                holder.postImg.visibility = View.GONE
                holder.exoPlayerView.visibility = View.VISIBLE
                simpleExoPlayer = VideoPlayback.buildPlayer(mContext)
                holder.exoPlayerView.player = simpleExoPlayer
                holder.exoFrameLayout.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, theItem.media.reddit_video.height)
                setVideo(dashURL = theItem.media.reddit_video.dash_url)
            }
        }



        adapter = AwardListAdapter(mContext, theItem.all_awardings)
        holder.awardRv.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        holder.awardRv.adapter = adapter

        Glide.with(mContext).load(getSubredditIconImg(theItem.subreddit_name_prefixed)).circleCrop().into(holder.subredditDp)


        //Listening for exoplayer event callbacks
        holder.exoPlayerView.player?.addListener(object :Player.EventListener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

                if (playbackState == Player.STATE_BUFFERING)
                    holder.videoProgressBar.visibility = View.VISIBLE
                else if (playbackState == Player.STATE_READY || playbackState == Player.STATE_ENDED) {
                    holder.videoProgressBar.visibility = View.INVISIBLE
                    Log.d("EXO","Event Listener called")
                }

            }
        })






    }


    //RUNTIME EXCEPTION --------- CLASS CAST EXCEPTION FLOAT TO INTEGER
    fun pxToDp(pxValue:Int):Int{
        val displayMetrics = mContext.getResources().getDisplayMetrics();
        return ((pxValue * displayMetrics.density) + 0.5) as Int
    }

    fun getSubredditIconImg(subredditName:String):String?{
        Log.d("getter","gets called with name $subredditName")
        for(sub in subredditMineListingChildren){
            if(sub.data.display_name_prefixed.equals(subredditName)) {

                Log.d("getter","was compared to ${sub.data.display_name_prefixed}")
                Log.d("getter","icon url ${sub.data.icon_img}")
                return sub.data.icon_img
            }
            else
                Log.d("getter","null")
        }
        return null
    }

    fun getVoteNofromInt(votes:Int):String{
        var number:Int = 0
        var symbol:String = ""
        var after_dec:Int = 0

        var div:Int = 1

        if(-1000>votes || votes>1000){
            div = 1000
            symbol = "k"
        }

        if(-1000000>votes || votes>1000000) {
            div = 1000000
            symbol = "m"
        }

        number = (votes/div)
        after_dec = votes%div

        if(after_dec==0)
            return "$number$symbol"
        return "$number.$after_dec$symbol"
    }

    fun setImage(theItem:PostData, img: ImageView){
        Glide.with(mContext).load(theItem.url).into(img)
    }

    fun setVideo(dashURL:String){

        VideoPlayback.dashURL = dashURL
        currentHolder.exoPlayerView.player = VideoPlayback.buildPlayer(mContext)
//        videoPlaybackService.initializePlayer()
        VideoPlayback.buildMediaSource()
        VideoPlayback.startPlayback()


    }

    override fun getItemCount(): Int {
        return postList.size
    }



}