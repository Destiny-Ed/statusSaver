package com.destinyed.statussaverpro.showImageAndVideo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.destinyed.statussaverpro.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class videoSavedFullScreen : AppCompatActivity() {

    //variable for admob
    private lateinit var mAdView : AdView
    private val mAppUnitId: String by lazy {

        "ca-app-pub-1700196351561262/9188899230"
        //test ads
//        "ca-app-pub-3940256099942544/6300978111"
    }

    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var shareVideo : FloatingActionButton

    private lateinit var videoView : VideoView
    private var videoUri : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_saved_full_screen)

        /**
         *InterstitialAds Implementation
         */
        MobileAds.initialize(this)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-1700196351561262/3337150215"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        /**
         * Banner |Ads Implementation
         */
        mAdView = findViewById(R.id.adView)
//
        initializeMobileAdBanner(mAppUnitId)
//
//        //Load banner ads
        loadBannerAd()

        shareVideo = findViewById(R.id.shareVideo)


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

        shareVideo.setOnClickListener {
            share_video(uri)
        }


    }

    private fun share_video(uri: Uri?) {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "video/*"

        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, "Share video via..."))
    }

    override fun onBackPressed() {
        super.onBackPressed()
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
    }

    /**
     * For banner Ads
     */
    private fun initializeMobileAdBanner(mAppUnitId: String) {
        MobileAds.initialize(this, mAppUnitId)
    }
    //banner ads completed
}