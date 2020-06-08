package com.destinyed.statussaverpro.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.destinyed.statussaverpro.Constants.ConstantsVariables
import com.destinyed.statussaverpro.Fragments.Videos
import com.destinyed.statussaverpro.Models.VideoModel
import com.destinyed.statussaverpro.R
import com.destinyed.statussaverpro.showImageAndVideo.videoFullScreen
import java.lang.Exception

class Video_Statust_Adapter(var ctx : Context, var statusArrVideo : ArrayList<VideoModel>, var videoFragment : Videos) : RecyclerView.Adapter<Video_Statust_Adapter.StatusHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Video_Statust_Adapter.StatusHolder {
        //inflate layout here
        var root =
            LayoutInflater.from(parent.context).inflate(R.layout.video_status_model, parent, false)
        return StatusHolder(root)
    }

    override fun getItemCount(): Int {
        return statusArrVideo.size
    }

    override fun onBindViewHolder(holder: Video_Statust_Adapter.StatusHolder, position: Int) {
        holder.bindItems(statusArrVideo[position])

    }

    inner class StatusHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        fun bindItems(status: VideoModel) {
            //get all items from custom layout
            var videoThumbnail = itemView.findViewById<ImageView>(R.id.videoThumbnail)
            var playBtn = itemView.findViewById<ImageButton>(R.id.playBtn)
            var saveBtn = itemView.findViewById<ImageButton>(R.id.saveBtn)


            videoThumbnail.setImageBitmap(status.video)

            playBtn.setOnClickListener {
                //set image uri to ImageFullScreen
                var intent = Intent(ctx, videoFullScreen::class.java)
                intent.putExtra("title", status.title)
                intent.putExtra("videoUri", status.path)
                ctx.startActivity(intent)
            }


            saveBtn.setOnClickListener {
                var statusDownload = statusArrVideo[adapterPosition]
                if (statusDownload != null){
                    try {
                        videoFragment.downloadVideo(statusDownload)
                        //show download toast
                        Toast.makeText(ctx, "Downloaded to ${ConstantsVariables.AppDirVideo}", Toast.LENGTH_LONG).show()
                    }
                    catch (e:Exception){
                        e.printStackTrace()
                    }
                }

            }

            /**
             * set animation to both saveBtn and playBtn
             */
            var animationRotate = AnimationUtils.loadAnimation(ctx, R.anim.rotate)

            playBtn.animation = animationRotate

        }

    }
}

//done