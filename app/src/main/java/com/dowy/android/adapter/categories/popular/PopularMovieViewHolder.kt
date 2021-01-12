package com.dowy.android.adapter.categories.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dowy.android.databinding.MainMovieItemBinding
import com.dowy.android.model.movie.Movie

class PopularMovieViewHolder(private val binding: MainMovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Movie?) {
        binding.movie = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): PopularMovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MainMovieItemBinding.inflate(inflater, parent, false)

            return PopularMovieViewHolder(binding)
        }
    }

}