package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemRowHistoryBinding
import com.example.myapplication.response.HistoryResponseItem

class ScanHistoryAdapter(private val scanHistoryList: List<HistoryResponseItem>) :
    RecyclerView.Adapter<ScanHistoryAdapter.ScanHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanHistoryViewHolder {
        val binding = ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScanHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScanHistoryViewHolder, position: Int) {
        holder.bind(scanHistoryList[position])
    }

    override fun getItemCount(): Int = scanHistoryList.size

    class ScanHistoryViewHolder(private val binding: ItemRowHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scanHistory: HistoryResponseItem) {
            with(binding) {
                tvName.text = scanHistory.name ?: "N/A"
                tvAge.text = scanHistory.age?.toString() ?: "N/A"
                tvGender.text = scanHistory.gender ?: "N/A"
                tvClassificationResult.text = scanHistory.message ?: "Unknown"
                Glide.with(itemView.context).load(scanHistory.photo).into(itemPhoto)
            }
        }
    }
}
