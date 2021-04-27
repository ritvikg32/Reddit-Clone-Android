package com.example.redditandroid.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redditandroid.R
import com.example.redditandroid.models.Award
import com.example.redditandroid.models.PostData

class AwardListAdapter(mContext:Context, awardList:ArrayList<Award>): RecyclerView.Adapter<AwardListAdapter.MyViewHolder>() {

    var awardList:ArrayList<Award> = ArrayList()

    val noItemsLimit:Int = 5
    var mContext:Context

    init {
        this.mContext = mContext
        this.awardList = awardList
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var awardImage:ImageView

        init {
            awardImage = itemView.findViewById(R.id.award_img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.award_rv_item, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return awardList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        setImage(awardList.get(position),holder.awardImage)
    }

    fun setImage(theItem: Award, img: ImageView){
        Glide.with(mContext).load(theItem.resized_icons.get(0).url).into(img)
    }
}