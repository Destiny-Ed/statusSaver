package com.destinyed.statussaverpro.RecyclerView

import retrofit2.Call
import retrofit2.http.GET

interface NewsInterface {

    @GET("v2/top-headlines?country=us&apiKey=6d346174a9054de7875e9cec6a1e9081")
    fun getNews() : Call<newsModel>

    @GET("v2/top-headlines?country=us&category=entertainment&apiKey=e53b8d7be4434d33a55970860760ed25")
    fun getAll() : Call<newsModel>
}