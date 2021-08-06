package com.example.redditandroid.ui.Fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.*

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.redditandroid.R
import com.example.redditandroid.api.UserAuthentication
import com.example.redditandroid.models.PostData1Children
import com.example.redditandroid.models.RedditVideo
import com.example.redditandroid.models.SubredditMineListing
import com.example.redditandroid.models.SubredditParentMine
import com.example.redditandroid.mv.TabHomeViewModel
import com.example.redditandroid.ui.Adapter.PostRecyclerView
import com.example.redditandroid.ui.Adapter.PostRvAdapter
import com.example.redditandroid.ui.Adapter.VoteCasted
import kotlinx.android.synthetic.main.fragment_tab_home.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"




class TabHomeFragment : androidx.fragment.app.Fragment(), UserAuthentication.Authentication, VoteCasted {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var postRv:PostRecyclerView
    lateinit var postRvAdapter:PostRvAdapter
    private lateinit var progressBar:ProgressBar
    lateinit var userAuth:UserAuthentication
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    var postList:ArrayList<PostData1Children> = ArrayList<PostData1Children>()
    var subredditMineListingChildren:ArrayList<SubredditParentMine> = ArrayList<SubredditParentMine>()

    val viewModel:TabHomeViewModel = TabHomeViewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userAuth = UserAuthentication(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if(viewModel.postList.value == null)
            viewModel.getPosts(false,"best")
        if(viewModel.subscribedSubredditList.value==null)
            viewModel.getSubscribedSubreddits()
    }

    var popupWindow:PopupWindow?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_home, container, false)

        val spinnerTxtArray = arrayOf<String>("Best", "Hot", "New", "Top", "Controversial", "Rating")
        val spinnerImgArray = arrayOf<Int>(R.drawable.ic_shuttle,R.drawable.ic_fire,R.drawable.ic_resource_new,R.drawable.ic_top_three,R.drawable.ic_flash,R.drawable.ic_success)

        postRv = view.findViewById(R.id.home_tab_rv)
        initRecyclerview()

        progressBar = view.findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout)

//        if(UserAuthentication.isUserAuthenticated)

        swipeRefreshLayout.setOnRefreshListener{
            updateRvData("best")
        }










        val filterPost = view.findViewById<Button>(R.id.filter_btn)

        filterPost.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                setPopUpWindow()

            }

        })






        return view
    }


    private fun updateRvData(filterType:String){
        progressBar.visibility = View.VISIBLE
        initRecyclerview()
        viewModel.getPosts(true, filterType)
    }

    private fun initRecyclerview(){
        postRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        postRvAdapter = activity?.applicationContext?.let { PostRvAdapter(it, initGlide(),this)}!!
        postRv.adapter = postRvAdapter
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
            .placeholder(R.drawable.white_background)
            .error(R.drawable.white_background)
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriebUI()
        val userAuth:UserAuthentication = UserAuthentication(this)

    }


    fun subscriebUI(){
        viewModel.postList.observe(viewLifecycleOwner, Observer {
            this.postList = it
            progressBar.visibility = View.GONE
            postRvAdapter.postList = it
            postRvAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
//            postRv.setMediaObjects(getVideoList(it))
        })

        viewModel.subscribedSubredditList.observe(viewLifecycleOwner, Observer {
            subredditMineListingChildren = it.children
            postRvAdapter.subredditMineListingChildren = subredditMineListingChildren
        })
    }


    fun setPopUpWindow(){

        val popup: PopupMenu = PopupMenu(context, filter_btn)
        popup.menuInflater.inflate(R.menu.tab_home_popup, popup.menu)

        popup.setOnMenuItemClickListener(object: PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.best_post -> updateRvData("best")
                    R.id.hot_post -> updateRvData("hot")
                    R.id.top_post -> updateRvData("top")
                    R.id.contr_post -> updateRvData("random")
                    R.id.rating_post -> updateRvData("rising")
                }

                return true
            }

        })
        popup.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TabHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TabHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onUserAuthenticated() {
        Log.d("post","User authenticated at tab home")
        viewModel.getPosts(false,"best")
        viewModel.getSubscribedSubreddits()
    }

    override fun onVoteCasted(id: String, dir: Int) {
        viewModel.castVote(id, dir)
    }
}