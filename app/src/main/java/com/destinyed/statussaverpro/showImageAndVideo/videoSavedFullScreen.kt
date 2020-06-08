package com.destinyed.statussaverpro.showImageAndVideo

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.destinyed.statussaverpro.R

class videoSavedFullScreen : AppCompatActivity() {

    //variable for admob
    private lateinit var mAdView : AdView
    private lateinit var mAdViewBelow : AdView
    private val mAppUnitId: String by lazy {

        "ca-app-pub-8573825847307688/4049243534"
        //test ads
//        "ca-app-pub-3940256099942544/6300978111"
    }

    private lateinit var mInterstitialAd: InterstitialAd

    private lateinit var videoView : VideoView
    private var videoUri : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_saved_full_screen)

        /**
         *InterstitialAds Implementation
         */
        MobileAds.initialize(this,
            "ca-app-pub-8573825847307688~4682000057")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-8573825847307688/9109998523"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        /**
         * Banner |Ads Implementation
         */
        mAdView = findViewById(R.id.adView)
        mAdViewBelow = findViewById(R.id.adViewBelow)

        initializeMobileAdBanner(mAppUnitId)

        //Load banner ads
        loadBannerAd()


        videoView = findViewById(R.id.videoShowFullScreen)
        var back = findViewById<ImageView>(R.id.goBack)

        //Get Uri path from VideoStatus
        var intent = intent.extras
        videoUri = intent!!.getString("videoUri")
        var uri = Uri.parse(videoUri)

        videoView.setVideoURI(uri)

        //show control button
        var mediaController : MediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)

        if (!videoView.isPlaying){
            videoView.start()
        }

        back.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onStart() {
        super.onStart()
        runAds()
    }

    private fun runAds() {
        mInterstitialAd.adListener = object : AdListener() {

            override fun onAdLoaded() {
                super.onAdLoaded()
                mInterstitialAd.show()
                if (videoView.isPlaying){
                    videoView.pause()
                }
            }
            override fun onAdClicked() {
                super.onAdOpened()
                mInterstitialAd.adListener.onAdClosed()
            }

            // If user closes the ad, s/he is directed to DetailActivity.
            override fun onAdClosed() {
                if (!videoView.isPlaying){
                    videoView.start()
                }
            }
        }
    }

    /**
     * For Banner Ads
     */
    private fun loadBannerAd() {
        var adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdViewBelow.loadAd(adRequest)
    }

    /**
     * For banner Ads
     */
    private fun initializeMobileAdBanner(mAppUnitId: String) {
        MobileAds.initialize(this, mAppUnitId)
    }
    //banner ads completed
}