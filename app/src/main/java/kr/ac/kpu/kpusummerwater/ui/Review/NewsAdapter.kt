package kr.ac.kpu.kpusummerwater.ui.Review

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.news_detail.view.*
import kr.ac.kpu.kpusummerwater.R


class NewsAdapter(val items : ArrayList<News.Item>, val context: Context) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_detail, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.itemView.tv_title?.text = items.get(position).title
        Glide
            .with(holder.itemView.context)
            .load(items.get(position)) //.thumb
            .centerCrop()
            .placeholder(android.R.drawable.stat_sys_upload)
            //.into(holder.itemView.iv_thumb)

        holder.itemView.setOnClickListener {

            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(items.get(position).url)
            ContextCompat.startActivity(context, openURL, null)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}