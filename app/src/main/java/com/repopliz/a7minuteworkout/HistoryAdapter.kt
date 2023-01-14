package com.repopliz.a7minuteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.repopliz.a7minuteworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(val items: ArrayList<HistoryEntity>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemBinding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val llHistoryItemMain = itemBinding.llHistoryItemMain
        val itemDate = itemBinding.tvItem
        val itemPosition = itemBinding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return (HistoryViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val date: String = items[position].date
        holder.itemDate.text = date
        holder.itemPosition.text = (position + 1).toString()

        if (position % 2 == 0) {
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#EBEBEB"))
        } else {
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}