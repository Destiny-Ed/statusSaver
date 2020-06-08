package com.destinyed.statussaverpro.Ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds

class intertitialAds(var ctx : Context) {

    lateinit var mInterstitialAd : InterstitialAd

    val InstiatiaAdUnitId : String by lazy {
        "ca-app-pub-3940256099942544/1033173712"
    }


    fun runAdEvents() {
        mInterstitialAd.adListener = object : AdListener() {

            // If user clicks on the ad and then presses the back, s/he is directed to DetailActivity.
            override fun onAdClicked() {
                super.onAdOpened()
                mInterstitialAd.adListener.onAdClosed()
            }

            // If user closes the ad, s/he is directed to DetailActivity.
            override fun onAdClosed() {
//                startActivity(Intent(this@MainActivity, BabyRecipes::class.java))
//                finish()
                onAdClosed()

            }
        }
    }

    //insterstitial ads
    fun loadInterstitialAd(interstitialAdUnitId: String) {

        mInterstitialAd.adUnitId = interstitialAdUnitId
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    fun initializeInterstitialAd(mAppUnitId: String) {
        MobileAds.initialize(ctx, mAppUnitId)
    }

}