package com.status.statussaverpro.SF

import android.content.Context
import android.content.SharedPreferences

class SFClass (ctx : Context) {

    val sharedPreference =  ctx.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)


    fun willShowImageAds() : Boolean {
        var editor = sharedPreference.edit()
        var getData = sharedPreference.getInt("count", 0)
        if(getData == 0){
            editor.putInt("count",1)
            editor.apply()
            return false;
        }else if (getData <= 3){
            editor.putInt("count",1 + getData)
            editor.apply()
            return false
        }else {
            editor.putInt("count",0)
            editor.apply()
            return true
        }
    }


    fun willShowVideoAds() : Boolean {
        var editor = sharedPreference.edit()
        var getData = sharedPreference.getInt("count2", 0)
        if(getData == 0){
            editor.putInt("count2",1)
            editor.apply()
            return false;
        }else if (getData <= 3){
            editor.putInt("count2",1 + getData)
            editor.apply()
            return false
        }else {
            editor.putInt("count2",0)
            editor.apply()
            return true
        }
    }
}