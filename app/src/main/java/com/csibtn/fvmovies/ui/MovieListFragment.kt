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
import com.csibtn.fvmovies.databinding.MovieListFragmentBinding
import com.csibtn.fvmovies.model.MovieViewModel
import kotlinx.coroutines.launch


class MovieListFragment : Fragment() {
    private var _binding: MovieListFragmentBinding? = null
    private val binding: MovieListFragmentBinding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MovieListFragmentBinding.inflate(inflater, container, false)
        binding.rvMovies.layoutManager = GridLayoutManager(context, 2)
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.lastQuery.collect { lastQuery ->
                binding.etSearchBar.setText(lastQuery)
            }
        }
        binding.etSearchBar.setOnEditorActionListener { textView, _, _ ->
            movieViewModel.setQuery(textView.text.toString())
            true
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycleScope.launch {
                movieViewModel.movies.collect { movies ->
                    binding.rvMovies.adapter = MoviesAdapter(movies.movieItems) { movie, extras ->
                        findNavController().navigate(
                            MovieListFragmentDirections.movieDetails(movie),
                            extras
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}