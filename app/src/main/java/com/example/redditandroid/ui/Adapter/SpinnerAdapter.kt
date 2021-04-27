package com.example.redditandroid.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.redditandroid.R


class SpinnerAdapter(context: Context, resource: Int, text: Array<String>, imageArray: Array<Int>): ArrayAdapter<String>(context, resource){


    val textArray = text
    val imageArray = imageArray



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup):View{

        val inflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view:View = inflater.inflate(R.layout.spinner_item,parent,false)
        val spinnerTextView = view.findViewById<TextView>(R.id.spinner_item_txt)
        val spinnerItemImg = view.findViewById<ImageView>(R.id.spinner_item_img)



        spinnerTextView.text = textArray[position]
        spinnerItemImg.setImageDrawable(context.resources.getDrawable(imageArray[position]))

        return view
    }
}