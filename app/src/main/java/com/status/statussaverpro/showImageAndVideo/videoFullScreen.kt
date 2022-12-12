package com.status.statussaverpro.showImageAndVideo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.*
import com.status.statussaverpro.Constants.ConstantsVariables
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.status.statussaverpro.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.status.statussaverpro.SF.SFClass
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.nio.channels.FileChannel

class videoFullScreen : AppCompatActivity() {

    //variable for admob
    private lateinit var mAdView : AdView


    private lateinit var videoView : VideoView
    private lateinit var shareVideo : FloatingActionButton
    private lateinit var downloadVideo : FloatingActionButton
    private var videoUri : String? = null
    private var videoTitle : String? = null

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_full_screen)

        var value = SFClass(this@videoFullScreen).willShowVideoAds()


        /**
         *InterstitialAds Implementation
         */
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-1420223449979323/2717885499", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd


                if(value){
                    ///Show ads
                    if (mInterstitialAd != null) {
                        mInterstitialAd?.show(this@videoFullScreen)
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                    }
                }
            }
        })


        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {


            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }

        /**
         * Banner |Ads Implementation
         */
        mAdView = findViewById(R.id.adView)
//        mAdViewBottom = findViewById(R.id.adViewBelow)

//        initializeMobileAdBanner(mAppUnitId)

        //Load banner ads
        loadBannerAd()

        shareVideo = findViewById(R.id.shareVideo)
        downloadVideo = findViewById(R.id.download)

        videoView = findViewById(R.id.videoShowFullScreen)
        var back = findViewById<ImageView>(R.id.goBack)

        //Get Uri path from VideoStatus
        var intent = intent.extras
        videoUri = intent!!.getString("videoUri")
        videoTitle = intent.getString("videoTitle")
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

        downloadVideo.setOnClickListener {
            downloadVideo(videoUri!!, videoTitle!!)

            Toast.makeText(this, "Downloaded to ${ConstantsVariables.AppDirImage}", Toast.LENGTH_LONG).show()
        }



    }

    private fun share_video(uri: Uri?) {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "video/*"

        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, "Share Video via..."))
    }


    fun downloadVideo(filePath: String, fileTitle : String) {
        var file = ConstantsVariables.AppDirVideo
        if (!file.exists()){
            file.mkdirs()
        }
        var destinationfile = File(file.toString(), fileTitle)

        if (!destinationfile.exists()){
            destinationfile.delete()
        }

        try {
            copyFile(File(filePath), destinationfile)

            //refresh gallery
            var intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = Uri.fromFile(destinationfile)
            this.sendBroadcast(intent)

        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    private fun copyFile(file: File, destinationfile: File) {

        if (!destinationfile.parentFile.exists()){
            destinationfile.parentFile.mkdirs()
        }

        if (!destinationfile.exists()){
            destinationfile.createNewFile()
        }

        var source : FileChannel? = null
        var destination : FileChannel? = null

        source = FileInputStream(file).channel
        destination = FileOutputStream(destinationfile).channel

        destination.transferFrom(source, 0, source.size())

        source.close()
        destination.close()


    }


    override fun onBackPressed() {
        super.onBackPressed()
//        runAds()
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
//    private fun initializeMobileAdBanner(mAppUnitId: String) {
//        MobileAds.initialize(this, mAppUnitId)
//    }
    //banner ads completed
}
