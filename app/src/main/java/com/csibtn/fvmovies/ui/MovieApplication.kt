package com.csibtn.fvmovies.ui

import android.app.Application
import com.csibtn.fvmovies.model.MovieDatabaseRepository
import com.csibtn.fvmovies.model.PreferencesRepository

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferencesRepository.initialize(this)
        MovieDatabaseRepository.initialize(this)
    }
}