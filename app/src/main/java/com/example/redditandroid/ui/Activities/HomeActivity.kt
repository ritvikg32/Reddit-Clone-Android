package com.example.redditandroid.ui.Activities

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.redditandroid.R
import com.example.redditandroid.api.UserAuthentication
import com.example.redditandroid.models.NavigationHeader
import com.example.redditandroid.models.NavigationLvModel
import com.example.redditandroid.mv.HomeFragmentMv
import com.example.redditandroid.ui.Adapter.navigation_view_adapter
import com.example.redditandroid.ui.Fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(){

    @RequiresApi(Build.VERSION_CODES.O)
    val modelView = HomeFragmentMv()


    lateinit var usernameText:TextView
    lateinit var karma_txt:TextView
    lateinit var redditAge:TextView
    lateinit var userProfilePic:ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        loadFragment(HomeFragment())



        val bottomNavBar:BottomNavigationView = findViewById(R.id.bottom_nav_view)

        usernameText= findViewById<TextView>(R.id.nav_username)
        karma_txt = findViewById<TextView>(R.id.karma_int)
        redditAge = findViewById<TextView>(R.id.age_int)
        userProfilePic=findViewById(R.id.header_profile_pic)


        bottomNavBar.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var fragment: Fragment? = null
                when (item.itemId) {
                    R.id.home_nav -> fragment = HomeFragment()
                }
                return loadFragment(fragment)
            }


        })

        val navigation_lv = findViewById<ListView>(R.id.navigation_view_lv)


        val navList = arrayOf<NavigationLvModel>(
            NavigationLvModel(
                "Profile",
                resources.getDrawable(R.drawable.ic_baseline_account_box_24)
            ),
            NavigationLvModel("Saved", resources.getDrawable(R.drawable.ic_baseline_save_24))
        )



        val myAdapter = navigation_view_adapter(this!!, navList)
        navigation_lv.adapter = myAdapter








        val navObserver = Observer<NavigationHeader>{
            usernameText.text = it.subreddit.display_name_prefixed
            karma_txt.text = it.total_karma.toString()


        }

        modelView.navigationHead?.observe(this, navObserver)

        modelView.imgBitmap.observe(this, Observer {
            userProfilePic.setImageBitmap(it)
        })



    }


    fun loadFragment(fragment: Fragment?):Boolean{
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottom_nav_frame, fragment)
                .commit();
            return true;
        }
        return false;
    }





}


