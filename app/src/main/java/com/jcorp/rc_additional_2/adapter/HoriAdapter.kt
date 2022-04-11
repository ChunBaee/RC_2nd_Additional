package com.jcorp.rc_additional_2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_additional_2.data.HoriItem
import com.jcorp.rc_additional_2.R
import com.jcorp.rc_additional_2.databinding.ItemRecyclerHorizontalBinding

class HoriAdapter : RecyclerView.Adapter<HoriAdapter.HoViewHolder>() {

    private var data = mutableListOf<HoriItem>()

    class HoViewHolder(val binding : ItemRecyclerHorizontalBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (item : HoriItem) {
            binding.horizontalTitle.text = item.horiTitle
            binding.horizontalType.text = item.horiType
            binding.horizontalReview.text = item.horiReview
            binding.horizontalWriterPhoto.setImageResource(item.horiWriteImage!!)
            binding.horizontalWriterName.text = item.horiWriteName
            //binding.horizontalReviewPhoto.setImageResource(item.horiReviewPhoto!!)
            binding.horizontalReviewPhoto.setBackgroundResource(item.horiReviewPhoto!!)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemRecyclerHorizontalBinding>(layoutInflater,
            R.layout.item_recycler_horizontal, parent, false)
        return HoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setList (list : MutableList<HoriItem>) {
        data = list.toMutableList()
        notifyDataSetChanged()
    }

}