package com.destinyed.statussaverpro.News

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.destinyed.statussaverpro.Constants.Constants
import com.destinyed.statussaverpro.R
import com.destinyed.statussaverpro.RecyclerView.NewsInterface
import com.destinyed.statussaverpro.RecyclerView.newsAdapter
import com.destinyed.statussaverpro.RecyclerView.newsModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_news_feed.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class NewsFeed : Fragment() {

    private lateinit var newsFeedView : RecyclerView
    private lateinit var adapter : newsAdapter

    private var arr = ArrayList<newsModel>()

    private lateinit var progress : ProgressBar

    private lateinit var mAdView : AdView
    private val mAppUnitId: String by lazy {

        "ca-app-pub-4496634947416290/9785820757"

        //test ads
//        "ca-app-pub-3940256099942544/6300978111"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_news_feed, container, false)

        /**
         * Banner ads
         */
        MobileAds.initialize(context,
            "ca-app-pub-4496634947416290~5962125810")
        /**
         * Banner |Ads Implementation
         */
        mAdView = root.findViewById(R.id.adView)

        initializeMobileAdBanner(mAppUnitId)

        //Load banner ads
        loadBannerAd()

        progress = root.findViewById(R.id.progress)

        newsFeedView = root.findViewById(R.id.newsFeedView)
        newsFeedView.layoutManager = LinearLayoutManager(context)

        try {
            getNewsRetrofit(context!!)
        }
        catch (e:Exception){
            toast("Error occurred. Please try again")
        }


        return root
    }


    //Retrofit Library
    private fun getNewsRetrofit(ctx : Context) {
        progress.visibility = View.VISIBLE
        var retrofit = Retrofit.Builder()
                .baseUrl("http://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var api = retrofit.create(NewsInterface::class.java)
        var call = api.getNews()

        //get the response
        call.enqueue(object : Callback<ArrayList<newsModel>> {
            override fun onFailure(call: Call<ArrayList<newsModel>>, t: Throwable) {
                Snackbar.make(snack, "Internet connection required. Please try again", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry"){
                        getNewsRetrofit(ctx)
                    }.show()
            }

            override fun onResponse(call: Call<ArrayList<newsModel>>, response: retrofit2.Response<ArrayList<newsModel>>) {
                var response = response.body()

                for(i in 0 until response!!.size){
                    var status = response[i].status
                    var articles = response[i].articles
                    var totalResult = response[i].totalResults
//                    var title = res.title
//                    var description = res.description
//                    var url = res.url
//                    var author = res.author
//                    var urlToImage = res.urlToImage
//
//                    var datePublished = res.publishedAt
//
//                    //formate the date
//                    var date = datePublished.substring(0, 10)
//                    var time = datePublished.substring(11, 16)
//                    var realDate = "$date  $time"

                    arr.add(newsModel(status, totalResult, articles))
                }

                //add the arr to the adapter
                adapter = newsAdapter(context!!, arr)
                newsFeedView.adapter = adapter
                adapter.notifyDataSetChanged()
                progress.visibility = View.GONE
            }


        })
    }

//    private fun getJson(ctx : Context) {
//        progress.visibility = View.VISIBLE
//
//        val queue = Volley.newRequestQueue(ctx)
//
//        val stringRequest = StringRequest(Request.Method.GET, Constants.jsonUrl,
//
//            Response.Listener { response ->
//
//                //Parse the json to the newsModel
//                var jsonObject = JSONObject(response)
//                var articles = jsonObject.getJSONArray("articles")
//                for (i in 0 until articles.length()){
//                    var jsonArticles = articles.getJSONObject(i)//get all string from this array
//                    //get the name from source
//                    var source = jsonArticles.getJSONObject("source")
//                    var name = source.getString("name")
//                    //get all string and parse to the arrayList<newsModel>
//                    var title = jsonArticles.getString("title")
//                    var description = jsonArticles.getString("description")
//                    var url = jsonArticles.getString("url")
//                    var urlToImage = jsonArticles.getString("urlToImage")
//                    var datePublished = jsonArticles.getString("publishedAt")
//
//                    //formate the date
//                    var date = datePublished.substring(0, 10)
//                    var time = datePublished.substring(11, 16)
//                    var realDate = "$date  $time"
//
//                    //add all string to the arrayList()
//                    arr.add(newsModel(title, name, description, realDate, urlToImage, url))
//                }
//
//                //add the arr to the adapter
//                adapter = newsAdapter(context!!, arr)
//                newsFeedView.adapter = adapter
//                adapter.notifyDataSetChanged()
//                progress.visibility = View.GONE
//            },
//            Response.ErrorListener {
//                Snackbar.make(snack, "Internet connection required. Please try again", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Retry"){
//                        getJson(ctx)
//                    }.show()
//
//            })
//
//        queue.add(stringRequest)
//
//
//    }

    /**
     * For Banner Ads
     */
    private fun initializeMobileAdBanner(mAppUnitId: String) {
        MobileAds.initialize(context, mAppUnitId)
    }
    /**
     * For Banner Ads
     */
    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    //Banner implementation ends

    private fun toast(message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}