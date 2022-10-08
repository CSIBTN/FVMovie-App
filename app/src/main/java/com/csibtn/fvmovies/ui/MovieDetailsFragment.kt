package com.csibtn.fvmovies.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.csibtn.fvmovies.R
import com.csibtn.fvmovies.api.Movie
import com.csibtn.fvmovies.databinding.MovieDetailsFragmentBinding
import com.csibtn.fvmovies.model.MovieViewModel
import com.csibtn.fvmovies.model.MoviesRepository
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val baseYoutubeUrl: String = "https://www.youtube.com/watch?v="

class MovieDetailsFragment : Fragment() {
    private val movieViewModel: MovieViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private var _binding: MovieDetailsFragmentBinding? = null
    private val binding: MovieDetailsFragmentBinding
        get() = checkNotNull(_binding)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        binding.rvCastList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            val movie : Movie? = movieViewModel.getMovie(args.movie.id)
            if (movie?.favourite == true) {
                binding.favourites.setImageResource(R.drawable.star_favourites_pressed)
            } else {
                binding.favourites.setImageResource(R.drawable.star_favourites)
            }

            if (movie?.currentlyWatching == true) {
                binding.currentlyWatching.setImageResource(R.drawable.bookmark_currently_watching_pressed)
            } else {
                binding.currentlyWatching.setImageResource(R.drawable.bookmark_currently_watching)
            }
            if (movie?.finished == true) {
                binding.finished.setImageResource(R.drawable.checked_finished_pressed)
            } else {
                binding.finished.setImageResource(R.drawable.checked_finished)
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.movie.poster_path == null)
            binding.ivDetailPoster.load(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/300px-No_image_available.svg.png"
            ) else {
            binding.ivDetailPoster.load(args.movie.posterUrl)
        }
        binding.tvMovieTitle.text = args.movie.title
        binding.tvMovieOverview.text = args.movie.overview
        binding.tvMovieReleaseDate.text = args.movie.release_date
        binding.progressBar.progress = (args.movie.score * 10).roundToInt()
        binding.tvProgressText.text = (args.movie.score * 10).roundToInt().toString()
        val movie = args.movie
        binding.favourites.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (!movie.favourite) {
                    movie.favourite = true
                    binding.favourites.setImageResource(R.drawable.star_favourites_pressed)
                    if (movieViewModel.getMovie(movie.id) == movie) {
                        movieViewModel.updateMovie(movie)
                    } else {
                        movieViewModel.addMovie(movie)
                    }
                    Toast.makeText(
                        context,
                        "Added ${movie.title} to the favourites",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    movie.favourite = false
                    binding.favourites.setImageResource(R.drawable.star_favourites)
                    movieViewModel.deleteMovie(movie)
                    Toast.makeText(
                        context,
                        "Deleted ${movie.title} from the favourites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.currentlyWatching.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (!movie.currentlyWatching) {
                    movie.currentlyWatching = true
                    binding.currentlyWatching.setImageResource(R.drawable.bookmark_currently_watching_pressed)
                    if (movieViewModel.getMovie(movie.id) == movie) {
                        movieViewModel.updateMovie(movie)
                    } else {
                        movieViewModel.addMovie(movie)
                    }
                    Toast.makeText(
                        context,
                        "Added ${movie.title} to the currently watching",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    movieViewModel.deleteMovie(movie)
                    movie.currentlyWatching = false
                    binding.currentlyWatching.setImageResource(R.drawable.bookmark_currently_watching)
                    Toast.makeText(
                        context,
                        "Deleted ${movie.title} from the currently watching",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.finished.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (!movie.finished) {
                    movie.finished = true
                    binding.finished.setImageResource(R.drawable.checked_finished_pressed)
                    if (movieViewModel.getMovie(movie.id) == movie) {
                        movieViewModel.updateMovie(movie)
                    } else {
                        movieViewModel.addMovie(movie)
                    }
                    Toast.makeText(
                        context,
                        "Added ${movie.title} to the finished",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    movie.finished = false
                    binding.finished.setImageResource(R.drawable.checked_finished)
                    movieViewModel.deleteMovie(movie)
                    Toast.makeText(
                        context,
                        "Deleted ${movie.title} from the finished",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val moviesRepository = MoviesRepository()
                val cast = moviesRepository.fetchMovieCast(args.movie.id)
                val trailerUrl: String = if (args.movie.poster_path != null) {
                    val movieTrailer = moviesRepository.fetchTrailerUrl(args.movie.id)
                    "$baseYoutubeUrl${movieTrailer.youtubeKey}"
                } else {
                    ""
                }
                binding.btnTrailer.setOnClickListener {
                    findNavController().navigate(
                        MovieDetailsFragmentDirections.showTrailer(trailerUrl)
                    )
                }
                binding.rvCastList.adapter = CastListAdapter(cast)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
