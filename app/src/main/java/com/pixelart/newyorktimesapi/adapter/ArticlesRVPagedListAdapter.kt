package com.pixelart.newyorktimesapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pixelart.newyorktimesapi.R
import com.pixelart.newyorktimesapi.common.BASE_IMAGE_URL
import com.pixelart.newyorktimesapi.common.GlideApp
import com.pixelart.newyorktimesapi.data.model.Doc
import com.pixelart.newyorktimesapi.data.model.Multimedium
import com.pixelart.newyorktimesapi.databinding.ArticleListContentBinding
import kotlinx.android.synthetic.main.article_list_content.view.*

class ArticlesRVPagedListAdapter(private val listener: OnItemClickedListener):
    PagedListAdapter<Doc, ArticlesRVPagedListAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesRVPagedListAdapter.ViewHolder {
        context = parent.context
        val view: ArticleListContentBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.article_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlesRVPagedListAdapter.ViewHolder, position: Int) {
        val doc = getItem(position)

        holder.setContent(doc!!)
        holder.itemView.setOnClickListener { listener.itemClickedListener(position) }
    }

    class ViewHolder(itemView: ArticleListContentBinding): RecyclerView.ViewHolder(itemView.root){

        fun setContent(doc: Doc){
            itemView.apply {
                tvArticleHeadline.text = doc.headline.main
                tvArticleSnippet.text = doc.snippet
                tvPublishDate.text = doc.pubDate
    
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
                    .into(ivArticleImage)
            }
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