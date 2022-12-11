package com.status.statussaverpro.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.status.statussaverpro.Fragments.Videos
import com.status.statussaverpro.Models.VideoModel
import com.status.statussaverpro.R
import com.status.statussaverpro.showImageAndVideo.videoFullScreen
import com.google.android.gms.ads.AdView

class Video_Statust_Adapter(var ctx : Context, var statusArrVideo : ArrayList<Any>, var videoFragment : Videos) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // A menu item view type.
    private val MENU_ITEM_VIEW_TYPE = 0

    // The banner ad view type.
    private val BANNER_AD_VIEW_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //inflate layout here
        return when (viewType) {
            MENU_ITEM_VIEW_TYPE -> {
                var root =
                    LayoutInflater.from(parent.context).inflate(R.layout.video_status_model, parent, false)

                return StatusHolder(root)
            }
            BANNER_AD_VIEW_TYPE -> {
                var root =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_ads, parent, false)

                return AdViewHolder(root)
            }
            else -> {
                var root =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_ads, parent, false)

                AdViewHolder(root)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (position % 8 == 0) BANNER_AD_VIEW_TYPE else MENU_ITEM_VIEW_TYPE

    }

    override fun getItemCount(): Int {
        return statusArrVideo.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            MENU_ITEM_VIEW_TYPE -> {
                val menuItemHolder: Video_Statust_Adapter.StatusHolder = holder as Video_Statust_Adapter.StatusHolder
                val models: VideoModel = statusArrVideo[position] as VideoModel
                menuItemHolder.bindItems(models)


            }
            BANNER_AD_VIEW_TYPE -> {
                val bannerHolder = holder as Video_Statust_Adapter.AdViewHolder
                val adView : AdView = statusArrVideo[position] as AdView
                val adCardView = bannerHolder.itemView as ViewGroup
                if (adCardView.childCount > 0) {
                    adCardView.removeAllViews()
                }
                if (adView.parent != null) {
                    (adView.parent as ViewGroup).removeView(adView)
                }
                adCardView.addView(adView)
            }
            else -> {
                val bannerHolder = holder as Video_Statust_Adapter.AdViewHolder
                val adView : AdView = statusArrVideo[position] as AdView
                val adCardView = bannerHolder.itemView as ViewGroup
                if (adCardView.childCount > 0) {
                    adCardView.removeAllViews()
                }
                if (adView.parent != null) {
                    (adView.parent as ViewGroup).removeView(adView)
                }
                adCardView.addView(adView)
            }
        }

    }

    inner class AdViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    )

    inner class StatusHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        fun bindItems(status: VideoModel) {
            //get all items from custom layout
            var videoThumbnail = itemView.findViewById<ImageView>(R.id.videoThumbnail)
            var playBtn = itemView.findViewById<ImageButton>(R.id.playBtn)
//            var saveBtn = itemView.findViewById<ImageButton>(R.id.saveBtn)


            videoThumbnail.setImageBitmap(status.video)

            playBtn.setOnClickListener {
                //set image uri to ImageFullScreen
                var intent = Intent(ctx, videoFullScreen::class.java)
                intent.putExtra("videoTitle", status.title)
                intent.putExtra("videoUri", status.path)
                ctx.startActivity(intent)
            }


//            saveBtn.setOnClickListener {
//                var statusDownload = statusArrVideo[adapterPosition]
//                if (statusDownload != null){
//                    try {
//                        videoFragment.downloadVideo(statusDownload)
//                        //show download toast
//                        Toast.makeText(ctx, "Downloaded to ${ConstantsVariables.AppDirVideo}", Toast.LENGTH_LONG).show()
//                    }
//                    catch (e:Exception){
//                        e.printStackTrace()
//                    }
//                }
//
//            }

            /**
             * set animation to both saveBtn and playBtn
             */
            var animationRotate = AnimationUtils.loadAnimation(ctx, R.anim.rotate)

            playBtn.animation = animationRotate

        }

    }
}

//done