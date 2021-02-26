package com.ericbatemandev.doordashlite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ericbatemandev.doordashlite.R
import com.ericbatemandev.doordashlite.databinding.ItemRestaurantBinding
import com.ericbatemandev.doordashlite.model.Restaurant

class RestaurantsAdapter : ListAdapter<Restaurant, RestaurantsAdapter.ViewHolder>(RestaurantsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    class ViewHolder(private val itemBinding: ItemRestaurantBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Restaurant) {
            itemBinding.apply {
                Glide.with(itemView.context).load(item.imgUrl).into(ivIcon)
                tvName.text = item.name
                tvDescription.text = item.description
                tvTime.text = itemView.context.getString(R.string.delivery_time, item.status.minutesRange[0])
            }
        }
    }
}

class RestaurantsDiffCallback : DiffUtil.ItemCallback<Restaurant>() {
    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem == newItem
    }
}