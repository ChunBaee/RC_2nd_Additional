package com.jcorp.rc_additional_2.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_additional_2.R
import com.jcorp.rc_additional_2.data.ChipItem
import com.jcorp.rc_additional_2.databinding.ItemLocationChipBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class ChipAdapter(context : Context) : RecyclerView.Adapter<ChipAdapter.ChipViewHolder>() {
    private var data = mutableListOf<ChipItem>()
    private val mContext = context

    class ChipViewHolder (val binding : ItemLocationChipBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ChipItem) {
            binding.btnChip.text = item.title
            when(item.isChecked) {
                true -> {
                    binding.imgChipChecked.visibility = View.VISIBLE
                }
                false -> {
                    binding.imgChipChecked.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemLocationChipBinding>(layoutInflater, R.layout.item_location_chip, parent, false)
        return ChipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        holder.bind(data[position])

        when(data[position].isChecked) {
            true -> {
                holder.binding.imgChipChecked.visibility = View.VISIBLE
                holder.binding.btnChip.setBackgroundResource(R.drawable.background_location_chip_selected_shape)
                holder.binding.btnChip.setTextColor(ContextCompat.getColor(mContext, R.color.near_pressed))
            }
            false -> {
                holder.binding.imgChipChecked.visibility = View.INVISIBLE
                holder.binding.btnChip.setBackgroundResource(R.drawable.background_location_chip_shape)
                holder.binding.btnChip.setTextColor(ContextCompat.getColor(mContext, R.color.less_gray))
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(list : MutableList<ChipItem>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }

}