package com.csibtn.fvmovies.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csibtn.fvmovies.api.Movie
import com.csibtn.fvmovies.api.Movies
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movies: MutableStateFlow<Movies> = MutableStateFlow(Movies(emptyList()))
    val movies: StateFlow<Movies>
        get() = _movies.asStateFlow()

    private val movieDatabaseRepository = MovieDatabaseRepository.get()
    private val moviesStateRepository: PreferencesRepository = PreferencesRepository.get()
    private val moviesRepository = MoviesRepository()

    val lastQuery = moviesStateRepository.storedQuery

    init {
        viewModelScope.launch {
            moviesStateRepository.storedQuery.collectLatest { storedQuery ->
                val movies = fetchMovieItems(storedQuery)
                _movies.update { oldMovies ->
                    oldMovies.copy(movieItems = movies)
                }
            }
        }
    }

    fun getFavourites() = movieDatabaseRepository.getFavourites()
    fun getCurrentlyWatching() = movieDatabaseRepository.getCurrentlyWatching()
    fun getFinished() = movieDatabaseRepository.getFinished()

    fun setQuery(query: String) {
        viewModelScope.launch { moviesStateRepository.setStoredQuery(query) }
    }

    suspend fun getMovie(movieId: Int) = movieDatabaseRepository.getMovie(movieId)

    suspend fun updateMovie(movie: Movie) = movieDatabaseRepository.updateMovie(movie)

    suspend fun addMovie(movie: Movie) {
        movieDatabaseRepository.insertMovie(movie)
    }

    suspend fun deleteMovie(movie: Movie) {
        movieDatabaseRepository.deleteMovie(movie)
    }

    private suspend fun fetchMovieItems(query: String): List<Movie> {
        return if (query.isNotEmpty()) {
            moviesRepository.findMovieOnQuery(query)
        } else {
            moviesRepository.fetchPopularMovies()
        }
    }

}
