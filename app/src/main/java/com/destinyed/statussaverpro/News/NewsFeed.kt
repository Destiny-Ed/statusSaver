package com.destinyed.statussaverpro.News

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.destinyed.statussaverpro.Constants.Constants
import com.destinyed.statussaverpro.R
import com.destinyed.statussaverpro.RecyclerView.newsAdapter
import com.destinyed.statussaverpro.RecyclerView.newsModel
import retrofit2.Retrofit

class NewsFeed : Fragment() {

    private lateinit var newsFeedView : RecyclerView
    private lateinit var adapter : newsAdapter

    private var arr = ArrayList<newsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_news_feed, container, false)

        newsFeedView = root.findViewById(R.id.newsFeedView)
        adapter = newsAdapter(context!!, arr)

        newsFeedView.layoutManager = LinearLayoutManager(context)

        getJson()


        return root
    }

    private fun getJson() {
        var retrofit = Retrofit.Builder()
            .baseUrl(Constants.jsonUrl)
            .build()


    }

}