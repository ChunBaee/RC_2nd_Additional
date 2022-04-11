package com.jcorp.rc_additional_2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_additional_2.data.GridItem
import com.jcorp.rc_additional_2.R
import com.jcorp.rc_additional_2.databinding.ItemRecyclerGridBinding

class GridAdapter : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {
    private var data = mutableListOf<GridItem>()
    private lateinit var clickListener : ClickListener

    interface ClickListener {
        fun onClick (view : View, position : Int)
    }
    fun clickListener (clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    class GridViewHolder (val binding : ItemRecyclerGridBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : GridItem) {
            binding.gridImage.setBackgroundResource(item.gridImage!!)
            binding.gridTitle.text = item.gridTitle
            binding.gridLocation.text = item.gridLocation
            binding.gridType.text = item.gridType
            binding.gridReviewPoint.text = item.gridRate.toString()
            binding.gridReviewCount.text = item.gridRateCount

            if(item.isBookmarked) {
                binding.gridBookmark.setBackgroundResource(R.drawable.icon_grid_filled_star)
            } else {
                binding.gridBookmark.setBackgroundResource(R.drawable.icon_grid_empty_star)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemRecyclerGridBinding>(layoutInflater,
            R.layout.item_recycler_grid, parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener {
            clickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData (list : MutableList<GridItem>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }
}