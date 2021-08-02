package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ViewholderAsteroidItemBinding
import com.udacity.asteroidradar.domain.Asteroid

class AsteroidAdapter(private val onItemClicked: AsteroidClick) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidItemViewHolder>(AsteroidDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidItemViewHolder {
        return AsteroidItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidItemViewHolder, position: Int) {
        val asteroidItem = getItem(position)
        holder.bind(asteroidItem, onItemClicked)
    }

    class AsteroidItemViewHolder(private val asteroidItemViewBinding: ViewholderAsteroidItemBinding) :
        RecyclerView.ViewHolder(asteroidItemViewBinding.root) {

        fun bind(item: Asteroid, itemClick: AsteroidClick) {

            asteroidItemViewBinding.asteroid = item
            itemView.setOnClickListener {
                itemClick.onClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidItemViewHolder {
                val binding = ViewholderAsteroidItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return AsteroidItemViewHolder(binding)
            }
        }

    }

}

class AsteroidClick(val block: (Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = block(asteroid)
}

class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {

    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}