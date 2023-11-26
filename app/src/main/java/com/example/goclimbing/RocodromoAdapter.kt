package com.example.goclimbing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RocodromoAdapter : ListAdapter<Rocodromo, RocodromoAdapter.RocodromoViewHolder>(RocodromoDiffCallback()) {
    var onItemClick: ((Rocodromo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocodromoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rocodromo_item, parent, false)
        return RocodromoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RocodromoViewHolder, position: Int) {
        val rocodromo = getItem(position)
        holder.bind(rocodromo)

    }

    class RocodromoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nombre: TextView = view.findViewById(R.id.nombre)

        fun bind(rocodromo: Rocodromo) {
            nombre.text = rocodromo.Nombre
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