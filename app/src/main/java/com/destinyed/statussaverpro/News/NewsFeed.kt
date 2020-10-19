package com.destinyed.statussaverpro.News

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.size
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.destinyed.statussaverpro.Constants.Constants
import com.destinyed.statussaverpro.R
import com.destinyed.statussaverpro.RecyclerView.Article
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
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class NewsFeed : Fragment() {

    private lateinit var newsFeedView : RecyclerView
    private lateinit var adapter : newsAdapter
    private lateinit var layoutManager : LinearLayoutManager

    private var arr = ArrayList<Article>()

    private lateinit var progress : ProgressBar

    private lateinit var mAdView : AdView
    private val mAppUnitId: String by lazy {

        "ca-app-pub-1700196351561262/9188899230"

        //test ads
//        "ca-app-pub-3940256099942544/6300978111"
    }

    var page = 1
    var isLoading = false
    var newsLimit = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_news_feed, container, false)

        /**
         * Banner ads
         */
        MobileAds.initialize(context)
        /**
         * Banner |Ads Implementation
         */
        mAdView = root.findViewById(R.id.adView)

        initializeMobileAdBanner(mAppUnitId)

        //Load banner ads
        loadBannerAd()

        progress = root.findViewById(R.id.progress)

        newsFeedView = root.findViewById(R.id.newsFeedView)

        layoutManager = LinearLayoutManager(context)

        newsFeedView.layoutManager = layoutManager

        newsFeedView.setHasFixedSize(false)

        try {
            getNewsRetrofit(context!!)
        }
        catch (e:Exception){
            toast("Error occurred. Please try again")
        }

        newsFeedView.itemAnimator




        return root
    }


    //Retrofit Library
    private fun getNewsRetrofit(ctx : Context) {
        progress.visibility = View.VISIBLE

        var retrofit = Retrofit.Builder()
            .baseUrl("http://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var callClient = retrofit.create(NewsInterface::class.java).getNews()

        callClient.enqueue(object : Callback<newsModel> {

            override fun onFailure(call: Call<newsModel>, t: Throwable) {
                Snackbar.make(snack, "Internet connection required. Please try again", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry"){
                        getNewsRetrofit(ctx)
                    }.show()
                progress.visibility = View.GONE
            }

            override fun onResponse(call: Call<newsModel>, response: Response<newsModel>) {
//                Log.i("Response", response.body()!!.toString())



                val responseNews = response.body()!!.articles

                adapter = newsAdapter(context!!, responseNews)

                newsFeedView.adapter = adapter
                adapter.notifyDataSetChanged()

                progress.visibility = View.GONE


            }
        })

    }

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