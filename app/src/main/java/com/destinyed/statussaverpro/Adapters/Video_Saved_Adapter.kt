package com.destinyed.statussaverpro.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.destinyed.statussaverpro.Models.Models
import com.destinyed.statussaverpro.R
import com.destinyed.statussaverpro.showImageAndVideo.videoSavedFullScreen

class Video_Saved_Adapter(var ctx : Context, var statusArr : ArrayList<Models>) : RecyclerView.Adapter<Video_Saved_Adapter.StatusHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Video_Saved_Adapter.StatusHolder {
        //inflate layout here
        var root =
            LayoutInflater.from(parent.context).inflate(R.layout.video_saved_model, parent, false)
        return StatusHolder(root)
    }

    override fun getItemCount(): Int {
        return statusArr.size
    }

    override fun onBindViewHolder(holder: Video_Saved_Adapter.StatusHolder, position: Int) {
        holder.bindItems(statusArr[position])

    }

    inner class StatusHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindItems(status: Models) {
            //get all items from custom layout
            var videoThumbnail = itemView.findViewById<ImageView>(R.id.videoThumbnail)
            var playBtn = itemView.findViewById<ImageButton>(R.id.playBtn)


            videoThumbnail.setImageBitmap(status.image)

            playBtn.setOnClickListener {
                //set image uri to ImageFullScreen
                var intent = Intent(ctx, videoSavedFullScreen::class.java)
                intent.putExtra("title", status.title)
                intent.putExtra("videoUri", status.path)
                ctx.startActivity(intent)
            }

            var animation = AnimationUtils.loadAnimation(ctx, R.anim.rotate)
            playBtn.animation = animation

            itemView.setOnClickListener {
                var dialog = AlertDialog.Builder(ctx)
                dialog.setMessage("You can't recover delete file. Do you want to proceed?")
                dialog.setNeutralButton("No. Cancel", null)
                dialog.setPositiveButton("Yes. Delete Forever") {
                    _, _ ->
                    status.file.delete()
                }
                dialog.create().show()
            }


        }

    }

}