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
import java.text.SimpleDateFormat
import java.util.*

class newsAdapter(var ctx : Context, var arr : List<Article>) : RecyclerView.Adapter<newsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsAdapter.ViewHolder {
        var view = LayoutInflater.from(ctx).inflate(R.layout.news_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: newsAdapter.ViewHolder, position: Int) {
        holder.setIsRecyclable(true)
        return holder.bindItems(arr[position])

    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(model : Article){
            var newsSource = itemView.findViewById<TextView>(R.id.newsSource)
            var title = itemView.findViewById<TextView>(R.id.newsTitle)
            var description = itemView.findViewById<TextView>(R.id.newsDescription)
            var date = itemView.findViewById<TextView>(R.id.newsDate)
            var image = itemView.findViewById<ImageView>(R.id.newsImage)

            newsSource.text = model.source.name
            title.text = model.title
            description.text = model.description

            var dateString = model.publishedAt

            //format the date and time
            var mDate = dateString.substring(0, 10)
            var mTime = dateString.substring(11, 16)
            var realDate = "$mDate  $mTime"


            date.text = realDate

            Picasso.get().load(model.urlToImage).into(image)


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