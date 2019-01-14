package com.pixelart.newyorktimesapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pixelart.newyorktimesapi.R
import com.pixelart.newyorktimesapi.common.BASE_IMAGE_URL
import com.pixelart.newyorktimesapi.common.GlideApp
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.model.Multimedium

class ArticlesRVPagedListAdapter(private val listener: OnItemClickedListener): PagedListAdapter<Doc, ArticlesRVPagedListAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesRVPagedListAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.article_list_content, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ArticlesRVPagedListAdapter.ViewHolder, position: Int) {
        val doc = getItem(position)

        holder.setContent(doc!!)
        holder.itemView.setOnClickListener { listener.itemClickedListener(position) }
    }

    class ViewHolder(itemView: View, private val context: Context): RecyclerView.ViewHolder(itemView){
        private val headline: TextView = itemView.findViewById(R.id.tvArticleHeadline)
        private val snippet: TextView = itemView.findViewById(R.id.tvArticleSnippet)
        private val pubDate: TextView = itemView.findViewById(R.id.tvPublishDate)
        private val image: ImageView = itemView.findViewById(R.id.ivArticleImage)

        fun setContent(doc: Doc){
            headline.text = doc.headline.main
            snippet.text = doc.snippet
            pubDate.text = doc.pubDate

            val multimedia: ArrayList<Multimedium> = doc.multimedia as ArrayList<Multimedium>
            var thumbnail = ""

            for (media in multimedia)
            {
                if (media.type == "image" && media.subtype == "thumbnail"){
                    thumbnail = BASE_IMAGE_URL + media.url
                    break
                }
            }

            GlideApp.with(context).load(thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.news_image_placeholder)
                .into(image)
        }
    }

    interface OnItemClickedListener{
        fun itemClickedListener(position: Int)
    }

    companion object {
        val DIFF_CALLBACK : DiffUtil.ItemCallback<Doc> = object : DiffUtil.ItemCallback<Doc>(){
            override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean {
                return oldItem == newItem
            }
        }
    }
}