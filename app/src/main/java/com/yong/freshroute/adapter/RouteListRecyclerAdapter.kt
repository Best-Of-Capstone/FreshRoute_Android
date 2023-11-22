package com.yong.freshroute.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.yong.freshroute.R
import com.yong.freshroute.util.RouteApiResultItem

class RouteListRecyclerAdapter(dataList: List<RouteApiResultItem>): Adapter<RouteListRecyclerAdapter.ViewHolder>() {
    private val dataList: List<RouteApiResultItem>
    var itemClick: ItemClick? = null

    init {
        this.dataList = dataList
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvDescription: TextView
        val tvDistance: TextView
        val tvDuration: TextView
        val tvID: TextView

        init {
            tvDescription = view.findViewById(R.id.recycler_item_routelist_description)
            tvDistance = view.findViewById(R.id.recycler_item_routelist_distance)
            tvDuration = view.findViewById(R.id.recycler_item_routelist_duration)
            tvID = view.findViewById(R.id.recycler_item_routelist_id)
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
        holder.tvDescription.text = listItem.description
        holder.tvDistance.text = listItem.route.distance
        holder.tvDuration.text = listItem.route.duration
        holder.tvDuration.text = String.format("Route %s", listItem.id)
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