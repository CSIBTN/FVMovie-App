package com.csibtn.fvmovies.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.csibtn.fvmovies.api.Movie
import com.csibtn.fvmovies.databinding.MovieItemCardviewBinding

class MoviesAdapter(
    private val listOfMovies: List<Movie>,
    private val onPosterClicked: (movieId: Movie, extras: FragmentNavigator.Extras) -> Unit
) :
    RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {

    inner class MoviesHolder(private val movieBinding: MovieItemCardviewBinding) :
        RecyclerView.ViewHolder(movieBinding.root) {
        fun bind(
            movie: Movie,
            onPosterClicked: (movie: Movie, extras: FragmentNavigator.Extras) -> Unit
        ) {
            if (movie.poster_path != null)
                movieBinding.ivPoster.load(movie.posterUrl)
            else {
                movieBinding.ivPoster.load("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/300px-No_image_available.svg.png")
            }
            movieBinding.tvItemTitle.text = movie.title
            movieBinding.root.setOnClickListener {
                val extras = FragmentNavigatorExtras(movieBinding.root to "detailsFragment")
                onPosterClicked(movie, extras)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val movieBinding = MovieItemCardviewBinding.inflate(inflater, parent, false)
        return MoviesHolder(movieBinding)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val movie = listOfMovies[position]
        holder.bind(movie, onPosterClicked)
    }

    override fun getItemCount(): Int = listOfMovies.size

}