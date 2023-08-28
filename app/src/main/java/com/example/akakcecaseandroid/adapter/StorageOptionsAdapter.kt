package com.example.akakcecaseandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.akakcecaseandroid.R

class StorageOptionsAdapter(private val storageOptions: List<String>) : RecyclerView.Adapter<StorageOptionsAdapter.StorageOptionsViewHolder>() {
    inner class StorageOptionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val storageText : TextView = itemView.findViewById(R.id.storage)
        fun bindData(storage: String) {
            storageText.text = storage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageOptionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_storage_options,parent,false)
        return StorageOptionsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return storageOptions.size
    }

    override fun onBindViewHolder(holder: StorageOptionsViewHolder, position: Int) {
        val storage = storageOptions[position]
        holder.bindData(storage)
    }
}