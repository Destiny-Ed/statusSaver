package com.status.statussaverpro.showImageAndVideo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.print.PrintHelper
import com.google.android.gms.ads.*
import com.status.statussaverpro.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ImageSavedFullScreen : AppCompatActivity() {

    //variable for admob
    private lateinit var mAdView : AdView
    private val mAppUnitId: String by lazy {

        "cca-app-pub-1700196351561262/9188899230"

        //test ads
//        "ca-app-pub-3940256099942544/6300978111"
    }
    private lateinit var printImage : FloatingActionButton
    private lateinit var shareImage : FloatingActionButton

    private lateinit var showImage : ImageView

    private lateinit var bitmap : Bitmap

    private var imageTitle : String? = null

//    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_saved_full_screen)

        /**
         *InterstitialAds Implementation
         */
        MobileAds.initialize(this)
//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = "ca-app-pub-1700196351561262/3337150215"
//        mInterstitialAd.loadAd(AdRequest.Builder().build())

        /**
         * Banner |Ads Implementation
         */
        mAdView = findViewById(R.id.adView)

//        initializeMobileAdBanner(mAppUnitId)

        //Load banner ads
        loadBannerAd()

//        runAds()


        showImage = findViewById(R.id.showSavedImageFullScreen)
        var goBack = findViewById<ImageView>(R.id.goBack)
        printImage = findViewById(R.id.printImage)
        shareImage = findViewById(R.id.shareImage)

        //get ImageUri from StatusAdapter Intent
        var intent = intent.extras
        var imageUri = intent!!.getString("imageUri")
        imageTitle = intent!!.getString("title")

        var uri = Uri.parse(imageUri)

        //set image to ImageView
        showImage.setImageURI(uri)

        bitmap = (showImage.drawable as BitmapDrawable).bitmap


        goBack.setOnClickListener {
            onBackPressed()
        }

        shareImage.setOnClickListener {
            doShare_Image(uri)
        }

        printImage.setOnClickListener {
            doPrint_Image(imageUri, imageTitle)
        }

    }

    private fun doPrint_Image(uri : String?, title : String?) {
        PrintHelper(baseContext).apply {
            PrintHelper.SCALE_MODE_FIT
        }.also {
            val bitmap = BitmapFactory.decodeFile(uri)
            it.printBitmap(title!!, bitmap)
        }

    }

    private fun doShare_Image(imageUri: Uri?) {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"

        intent.putExtra(Intent.EXTRA_STREAM, imageUri)
        startActivity(Intent.createChooser(intent, "Share Image via..."))
    }


//    private fun runAds() {
//        mInterstitialAd.adListener = object : AdListener() {
//
//            override fun onAdLoaded() {
//                super.onAdLoaded()
//                mInterstitialAd.show()
//            }
//            override fun onAdClicked() {
//                super.onAdOpened()
//                mInterstitialAd.adListener.onAdClosed()
//            }
//
//            // If user closes the ad, s/he is directed to DetailActivity.
//            override fun onAdClosed() {
//
//
//            }
//        }
//
//    }

    /**
     * For Banner Ads
     */
//    private fun initializeMobileAdBanner(mAppUnitId: String) {
//        MobileAds.initialize(this, mAppUnitId)
//    }
    /**
     * For Banner Ads
     */
    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    //Banner implementation ends

}
