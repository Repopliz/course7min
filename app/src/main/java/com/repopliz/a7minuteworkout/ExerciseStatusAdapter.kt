package com.repopliz.a7minuteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.repopliz.a7minuteworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.MainViewHolder>() {

    inner class MainViewHolder(val itemBinding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val tvItem = itemBinding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val currentItem = items[position]
        holder.tvItem.text = currentItem.getId().toString()

        when {
            currentItem.getIsSelected() -> {
                holder.itemBinding.tvItem.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_thin_color_accent_border
                )
                holder.itemBinding.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            currentItem.getIsCompleted() -> {
                holder.itemBinding.tvItem.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_color_accent_background
                )
                holder.itemBinding.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.itemBinding.tvItem.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_color_gray_background
                )
                holder.itemBinding.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}