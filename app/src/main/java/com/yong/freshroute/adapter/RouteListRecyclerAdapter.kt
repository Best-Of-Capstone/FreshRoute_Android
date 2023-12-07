package com.yong.freshroute.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.yong.freshroute.R
import com.yong.freshroute.util.RouteApiResultItem
import java.util.Locale

class RouteListRecyclerAdapter(dataList: List<RouteApiResultItem>): Adapter<RouteListRecyclerAdapter.ViewHolder>() {
    private val dataList: List<RouteApiResultItem>
    var itemClick: ItemClick? = null

    init {
        this.dataList = dataList
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvDescription: TextView
        val tvTitle: TextView

        init {
            tvDescription = view.findViewById(R.id.recycler_item_routelist_description)
            tvTitle = view.findViewById(R.id.recycler_item_routelist_title)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RouteListRecyclerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_route_list, parent, false))
    }

    override fun onBindViewHolder(holder: RouteListRecyclerAdapter.ViewHolder, position: Int) {
        val listItem = dataList[position]
        holder.tvDescription.text = String.format(holder.itemView.context.getString(R.string.list_recycler_route_description), listItem.route.distance.toDouble() / 1000, listItem.route.duration.toDouble() / 60, listItem.route.duration.toDouble() % 60)
        holder.tvTitle.text = String.format(holder.itemView.context.getString(R.string.list_recycler_route_title), listItem.id)
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