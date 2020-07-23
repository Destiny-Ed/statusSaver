package com.destinyed.statussaverpro.RecyclerView

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.destinyed.statussaverpro.News.NewsFeed
import com.destinyed.statussaverpro.News.NewsRead
import com.destinyed.statussaverpro.R
import com.squareup.picasso.Picasso

class newsAdapter(var ctx : Context, var arr : ArrayList<newsModel>) : RecyclerView.Adapter<newsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsAdapter.ViewHolder {
        var view = LayoutInflater.from(ctx).inflate(R.layout.news_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: newsAdapter.ViewHolder, position: Int) {
        return holder.bindItems(arr[position])
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(model : newsModel){
            var newsSource = itemView.findViewById<TextView>(R.id.newsSource)
            var title = itemView.findViewById<TextView>(R.id.newsTitle)
            var description = itemView.findViewById<TextView>(R.id.newsDescription)
            var date = itemView.findViewById<TextView>(R.id.newsDate)
            var image = itemView.findViewById<ImageView>(R.id.newsImage)

            newsSource.text = model.source
            title.text = model.title
            description.text = model.description
            date.text = model.publishedDate

            Picasso.with(ctx).load(model.urlToImage).fit().into(image)

            itemView.setOnClickListener {
                //send the url to newsRead activity
                var intent = Intent(ctx, NewsRead::class.java)
                intent.putExtra("url", model.url)
                ctx.startActivity(intent)
            }
        }
    }
}

//Done