package com.example.goclimbing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RocodromoAdapter(private val onItemClickListener: OnItemClickListener) : ListAdapter<Rocodromo, RocodromoAdapter.RocodromoViewHolder>(RocodromoDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(rocodromo: Rocodromo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocodromoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rocodromo_item, parent, false)
        return RocodromoViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RocodromoViewHolder, position: Int) {
        val rocodromo = getItem(position)
        holder.bind(rocodromo)
    }

    class RocodromoViewHolder(view: View, private val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val nombre: TextView = view.findViewById(R.id.nombre)

        private lateinit var currentRocodromo: Rocodromo

        init {
            view.setOnClickListener(this)
        }

        fun bind(rocodromo: Rocodromo) {
            currentRocodromo = rocodromo
            nombre.text = rocodromo.Nombre
        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(currentRocodromo)
        }
    }
}

class RocodromoDiffCallback : DiffUtil.ItemCallback<Rocodromo>() {
    override fun areItemsTheSame(oldItem: Rocodromo, newItem: Rocodromo): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Rocodromo, newItem: Rocodromo): Boolean {
        return oldItem == newItem
    }
}
