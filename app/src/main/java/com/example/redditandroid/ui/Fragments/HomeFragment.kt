package com.example.redditandroid.ui.Fragments

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import android.widget.Spinner
import androidx.core.graphics.drawable.toBitmap
import androidx.viewpager.widget.ViewPager
import com.example.redditandroid.R
import com.example.redditandroid.models.NavigationLvModel
import com.example.redditandroid.ui.Adapter.HomeTabAdapter
import com.example.redditandroid.ui.Adapter.SpinnerAdapter
import com.example.redditandroid.ui.Adapter.navigation_view_adapter
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 * create an instance of this fragment.
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var mContext:Context? = null
    lateinit var searchView:SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val viewPager = view.findViewById<ViewPager>(R.id.home_view_pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.home_tab_layout)
        searchView = view.findViewById<SearchView>(R.id.home_search)




        val pagerAdapter = HomeTabAdapter(activity!!.supportFragmentManager,context!!)
        viewPager.adapter = pagerAdapter

        tabLayout.setupWithViewPager(viewPager)

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}