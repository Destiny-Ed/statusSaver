package com.status.statussaverpro.showImageAndVideo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.print.PrintHelper
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.status.statussaverpro.Constants.ConstantsVariables
import com.status.statussaverpro.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.status.statussaverpro.SF.SFClass
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class ImageFullScreen : AppCompatActivity() {

    //variable for admob
    private lateinit var mAdView : AdView

    private lateinit var showImage : ImageView

    private lateinit var bitmap : Bitmap

    private lateinit var download : FloatingActionButton
    private lateinit var print : FloatingActionButton
    private lateinit var share_image : FloatingActionButton


    var imageTitle : String? = null

    ///Ads
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_full_screen)

        /**
         *InterstitialAds Implementation
         */
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd

                var value = SFClass(this@ImageFullScreen).willShowImageAds()

                if(value){
                    ///Show ads
                    if (mInterstitialAd != null) {
                        mInterstitialAd?.show(this@ImageFullScreen)
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

//        initializeMobileAdBanner(mAppUnitId)

        //Load banner ads
        loadBannerAd()

//        runAds()

        /**
         * Initialize id
         */
        showImage = findViewById(R.id.showImageFullScreen)
        download = findViewById(R.id.download)
        print = findViewById(R.id.printImage)
        share_image = findViewById(R.id.shareImage)

        var goBack = findViewById<ImageView>(R.id.goBack)

        //get ImageUri from StatusAdapter Intent
        var intent = intent.extras
        var imageUri = intent!!.getString("imageUri")
        imageTitle = intent.getString("title")

        var uri = Uri.parse(imageUri)

        //set image to ImageView
        showImage.setImageURI(uri)

        bitmap = (showImage.drawable as BitmapDrawable).bitmap

        download.setOnClickListener {
            downloadImage()
//            runAds()
        }

        goBack.setOnClickListener {
            onBackPressed()
        }

        print.setOnClickListener {
            doImage_print(imageUri, imageTitle)
        }

        share_image.setOnClickListener {
            doImage_share(uri)
        }

    }



    private fun doImage_share(imageUri: Uri?) {
        var arr : Any = arrayOf(imageUri, "hello")
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"

        intent.putExtra(Intent.EXTRA_STREAM, imageUri)
        startActivity(Intent.createChooser(intent, "Share Image"))
    }

    private fun doImage_print(imageUri: String?, imageTitle : String?) {
        PrintHelper(this).apply {
            PrintHelper.SCALE_MODE_FIT
        }.also {
            var bitmap = BitmapFactory.decodeFile(imageUri)
            it.printBitmap(imageTitle!!, bitmap)
        }
    }


//    private fun runAds() {
//        mInterstitialAd.adListener = object : AdListener() {
//
//            override fun onAdLoaded() {
//                super.onAdLoaded()
//                mInterstitialAd.show()
//            }
//
//            override fun onAdClicked() {
//                super.onAdOpened()
//                mInterstitialAd.adListener.onAdClosed()
//            }
//
//            // If user closes the ad, s/he is directed to DetailActivity.
//            override fun onAdClosed() {
//
//            }
//        }
//    }

    /**
     * For Banner Ads
     */
//    private fun initializeMobileAdBanner(mAppUnitId: String) {
//        MobileAds.initialize(this, )
//    }
    /**
     * For Banner Ads
     */
    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    //Banner implementation ends

    private fun downloadImage() {
        val AppDir = ConstantsVariables.AppDirImage
        if (!AppDir.exists()){
            AppDir.mkdirs()
        }

        try {
            val file = File(AppDir, imageTitle!!)
            val out = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
            out.close()
        }catch (e:Exception){
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
        }

    }

}
