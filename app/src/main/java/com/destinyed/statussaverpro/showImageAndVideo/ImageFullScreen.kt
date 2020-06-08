package com.destinyed.statussaverpro.showImageAndVideo

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.ads.*
import com.destinyed.statussaverpro.Constants.ConstantsVariables
import com.destinyed.statussaverpro.R
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class ImageFullScreen : AppCompatActivity() {

    //variable for admob
    private lateinit var mAdView : AdView
    private val mAppUnitId: String by lazy {

        "ca-app-pub-8573825847307688/4049243534"

        //test ads
//        "ca-app-pub-3940256099942544/6300978111"
    }

    private lateinit var showImage : ImageView

    private lateinit var bitmap : Bitmap

    lateinit var download : Button

    private var imageTitle : String? = null

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_full_screen)

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

        initializeMobileAdBanner(mAppUnitId)

        //Load banner ads
        loadBannerAd()


        showImage = findViewById(R.id.showImageFullScreen)
        download = findViewById<Button>(R.id.download)
        var goBack = findViewById<ImageView>(R.id.goBack)

        //get ImageUri from StatusAdapter Intent
        var intent = intent.extras
        var imageUri = intent!!.getString("imageUri")
        imageTitle = intent!!.getString("title")

        var uri = Uri.parse(imageUri)

        //set image to ImageView
        showImage.setImageURI(uri)

        bitmap = (showImage.drawable as BitmapDrawable).bitmap

        download.setOnClickListener {
            downloadImage()
            runAds()
        }

        goBack.setOnClickListener {
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
            }
            override fun onAdClicked() {
                super.onAdOpened()
                mInterstitialAd.adListener.onAdClosed()
            }

            // If user closes the ad, s/he is directed to DetailActivity.
            override fun onAdClosed() {

            }
        }

    }

    /**
     * For Banner Ads
     */
    private fun initializeMobileAdBanner(mAppUnitId: String) {
        MobileAds.initialize(this, mAppUnitId)
    }
    /**
     * For Banner Ads
     */
    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    //Banner implementation ends

    private fun downloadImage() {
        var AppDir = ConstantsVariables.AppDirImage
        if (!AppDir.exists()){
            AppDir.mkdirs()
        }

        try {
            var file = File(AppDir, imageTitle)
            var out = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
            out.close()
        }catch (e:Exception){
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
        }

    }



}
