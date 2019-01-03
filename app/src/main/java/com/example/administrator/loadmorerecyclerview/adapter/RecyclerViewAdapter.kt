package com.example.administrator.loadmorerecyclerview.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.administrator.loadmorerecyclerview.MyApplication
import com.example.administrator.loadmorerecyclerview.R
import com.example.administrator.loadmorerecyclerview.StarView
import com.example.administrator.loadmorerecyclerview.bean.Top250

class RecyclerViewAdapter constructor(context: Context, list: ArrayList<Top250.Subjects>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mList = list
    private val mContext = context
    var mOnLoadMoreDataListener: OnLoadMoreDataListener? = null
    var loadState: Int = 0

    companion object {
        const val CONTENT_TYPE: Int = 10
        const val LOADING_TYPE: Int = 11
        const val NORMAL = 0
        const val LOADING = 1
        const val NO_MORE = 2
    }


    class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val tvRating: TextView = itemView.findViewById(R.id.tv_rating)
        val tvGenres: TextView = itemView.findViewById(R.id.tv_genres)
        val starView:StarView = itemView.findViewById(R.id.star_view)
    }

    class LoadingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHint: TextView = itemView.findViewById(R.id.tv_hint)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
    }


    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        if (type == CONTENT_TYPE) {
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
            return ContentHolder(inflate)
        } else {
            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            return LoadingHolder(inflate)
        }
    }

    override fun getItemCount(): Int {
        return mList.size + 1
    }


    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        //加载更多
        if (mList.isNotEmpty() && p1 >= itemCount - 5 && loadState == NORMAL) {
            mOnLoadMoreDataListener?.onLoadMoreData()
            loadState = LOADING
        }

        if (p0 is ContentHolder) {
            p0.tvTitle.text = "${mList[p1].title}(${mList[p1].year})"
            p0.tvRating.text = "${mList[p1].rating?.average}"
            p0.starView.setRating(mList[p1].rating?.average)
            p0.tvGenres.text = "${mList[p1].genres}"
            Glide.with(mContext)
                    .load(mList.get(p1).images?.small)
                    .into(p0.imageView)
        } else if (p0 is LoadingHolder) {
            when(loadState){
                NORMAL ->{
                    p0.tvHint.visibility = View.GONE
                    p0.progressBar.visibility = View.GONE
                }
                NO_MORE ->{
                    p0.tvHint.visibility = View.VISIBLE
                    p0.progressBar.visibility = View.GONE
                }
                LOADING ->{
                    p0.tvHint.visibility = View.GONE
                    p0.progressBar.visibility = View.VISIBLE
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == mList.size) {
            return LOADING_TYPE
        }
        return CONTENT_TYPE
    }

    fun loadMore(position: Int) {
        loadState = NORMAL
        notifyItemInserted(position)
    }

    fun noMore() {
        loadState = NO_MORE
        notifyItemChanged(itemCount - 1)
    }

    fun refresh() {
        loadState = NORMAL
    }


    fun setOnLoadMoreDataListener(onLoadMoreDataListener: OnLoadMoreDataListener) {
        mOnLoadMoreDataListener = onLoadMoreDataListener
    }

    interface OnLoadMoreDataListener {
        fun onLoadMoreData()
    }
}