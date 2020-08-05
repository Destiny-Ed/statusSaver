package com.destinyed.statussaverpro.RecyclerView

import retrofit2.Call
import retrofit2.http.GET

interface NewsInterface {

    @GET("v2/top-headlines?country=us&apiKey=6d346174a9054de7875e9cec6a1e9081")
    fun getNews() : Call<ArrayList<newsModel>>

}