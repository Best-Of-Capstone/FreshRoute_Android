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
        val tvDescription: TextView
        val tvTitle: TextView

        init {
            tvDescription = view.findViewById(R.id.recycler_item_routedetail_description)
            tvTitle = view.findViewById(R.id.recycler_item_routedetail_title)
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

        if(listItem.distance != 0 && listItem.duration != 0){
            holder.tvDescription.text = String.format(holder.itemView.resources.getString(R.string.detail_recycler_route_description), listItem.distance.toDouble() / 1000, listItem.duration.toDouble() / 60, listItem.duration.toDouble() % 60)
        }
        holder.tvTitle.text = String.format(holder.itemView.resources.getString(R.string.detail_recycler_route_title), listItem.name, listItem.type)
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