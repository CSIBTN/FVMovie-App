package com.csibtn.fvmovies.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.csibtn.fvmovies.R
import com.csibtn.fvmovies.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcvList)
        val navController = navHostFragment?.findNavController()

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favourites -> {
                    navController?.navigate(
                        MovieListFragmentDirections.showFavouriteMovies()
                    )
                    true
                }
                R.id.currently_watching -> {
                    navController?.navigate(
                        MovieListFragmentDirections.showCurrentlyWatchedMovies()
                    )
                    true
                }
                R.id.finished -> {
                    navController?.navigate(
                        MovieListFragmentDirections.showFinishedMovies()
                    )
                    true
                }
                else -> false
            }
        }
    }


}