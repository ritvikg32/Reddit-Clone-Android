package com.example.redditandroid.ui.Adapter

import android.content.Context
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.redditandroid.R
import com.example.redditandroid.ui.Fragments.HomeFragment
import com.example.redditandroid.ui.Fragments.PopularFragment
import com.example.redditandroid.ui.Fragments.TabHomeFragment
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource

class HomeTabAdapter(fragmentManager:FragmentManager, mContext:Context) : FragmentStatePagerAdapter(fragmentManager) {

    val mContext:Context


    init {
        this.mContext = mContext
    }


    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        if(position==0)
           return TabHomeFragment()
        else
            return PopularFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {

        val tabTitle = arrayOf(mContext.resources.getStringArray(R.array.tab_title))
        return when(position){
            0 -> "Home"
            else -> "Popular"
        }

    }



}