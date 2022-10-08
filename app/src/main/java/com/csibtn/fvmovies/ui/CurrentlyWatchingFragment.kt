package com.csibtn.fvmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.csibtn.fvmovies.databinding.CurrenlyWatchingFragmentBinding
import com.csibtn.fvmovies.model.MovieViewModel
import kotlinx.coroutines.launch

class CurrentlyWatchingFragment : Fragment() {
    private val movieViewModel: MovieViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CurrenlyWatchingFragmentBinding.inflate(inflater, container, false)
        binding.currentlyWatchingList.layoutManager = GridLayoutManager(context, 2)
        val movies = movieViewModel.getCurrentlyWatching()
        viewLifecycleOwner.lifecycleScope.launch {
            movies.collect { movies ->
                binding.currentlyWatchingList.adapter = MoviesAdapter(movies) { movie, extras ->
                    findNavController().navigate(
                        MovieListFragmentDirections.movieDetails(movie),
                        extras
                    )
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(
                FavouritesFragmentDirections.showMovieList()
            )
        }
    }
}