package com.example.redditandroid.ui.Adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.redditandroid.models.NavigationLvModel
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.redditandroid.R

class navigation_view_adapter(val mContext:Activity, val list:Array<NavigationLvModel>) : ArrayAdapter<NavigationLvModel>(mContext,
    R.layout.custom_navigation_list,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = mContext.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_navigation_list, null, true)

        val list_icon = rowView?.findViewById<ImageView>(R.id.nav_list_icon)
        val label_txt = rowView?.findViewById<TextView>(R.id.nav_list_text)

        list_icon?.setImageDrawable(list.get(position).icon)
        label_txt?.setText(list.get(position).label)

        return rowView


    }
}