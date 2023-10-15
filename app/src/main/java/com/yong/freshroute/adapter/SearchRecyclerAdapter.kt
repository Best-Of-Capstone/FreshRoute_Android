package com.yong.freshroute.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.yong.freshroute.R
import com.yong.freshroute.util.KakaoLocalItem

class SearchRecyclerAdapter(dataList: List<KakaoLocalItem>): Adapter<SearchRecyclerAdapter.ViewHolder>() {
    private val dataList: List<KakaoLocalItem>
    private var itemClick: ItemClick? = null

    init {
        this.dataList = dataList
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvAddress: TextView
        val tvName: TextView

        init {
            tvAddress = view.findViewById(R.id.recycler_item_search_address)
            tvName = view.findViewById(R.id.recycler_item_search_name)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchRecyclerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_search, parent, false))
    }

    override fun onBindViewHolder(holder: SearchRecyclerAdapter.ViewHolder, position: Int) {
        val listItem = dataList[position]
        holder.tvAddress.text = listItem.localAddress
        holder.tvName.text = listItem.localName
        holder.itemView.setOnClickListener { view ->
            itemClick?.onClick(view, position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
}