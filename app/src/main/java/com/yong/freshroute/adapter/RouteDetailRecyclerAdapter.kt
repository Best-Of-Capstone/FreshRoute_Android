package com.yong.freshroute.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.yong.freshroute.R
import com.yong.freshroute.util.RouteApiResultItemDataStep

class RouteDetailRecyclerAdapter(dataList: List<RouteApiResultItemDataStep>): Adapter<RouteDetailRecyclerAdapter.ViewHolder>() {
    private val dataList: List<RouteApiResultItemDataStep>
    var itemClick: ItemClick? = null

    init {
        this.dataList = dataList
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvDistance: TextView
        val tvDuration: TextView
        val tvName: TextView
        val tvType: TextView

        init {
            tvDistance = view.findViewById(R.id.recycler_item_routedetail_distance)
            tvDuration = view.findViewById(R.id.recycler_item_routedetail_duration)
            tvName = view.findViewById(R.id.recycler_item_routedetail_name)
            tvType = view.findViewById(R.id.recycler_item_routedetail_type)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RouteDetailRecyclerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_route_detail, parent, false))
    }

    override fun onBindViewHolder(holder: RouteDetailRecyclerAdapter.ViewHolder, position: Int) {
        val listItem = dataList[position]
        holder.tvDistance.text = listItem.distance.toString()
        holder.tvDuration.text = listItem.duration.toString()
        holder.tvName.text = listItem.name
        holder.tvType.text = listItem.type
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