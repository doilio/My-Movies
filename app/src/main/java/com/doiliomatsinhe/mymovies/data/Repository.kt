package com.doiliomatsinhe.mymovies.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.model.TvSeries
import com.doiliomatsinhe.mymovies.model.asDomainModel
import com.doiliomatsinhe.mymovies.network.ApiService
import com.doiliomatsinhe.mymovies.utils.SECRET_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: ApiService,
    private val database: MoviesDao
) {

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            try {
                val listOfMovies = service.getMovies("popular", SECRET_KEY, "en-US", 1).results
                Timber.d("List of Movies Net: $listOfMovies")
                database.insertAllMovies(*listOfMovies.asDatabaseModel())
            } catch (e: Exception) {
                Timber.d("Error reading Movies ${e.message}")
            }
        }
    }

    fun getMovies(): LiveData<List<Movie>> {
        return Transformations.map(database.getMovies()) {
            it.asDomainModel()
        }
    }

    suspend fun refreshSeries() {
        withContext(Dispatchers.IO) {
            try {
                val listOfSeries = service.getSeries("popular", SECRET_KEY, "en-US", 1).results
                Timber.d("List of Series Net: $listOfSeries")
                database.insertAllSeries(*listOfSeries.asDatabaseModel())
            } catch (e: Exception) {
                Timber.d("Error reading Series ${e.message}")
            }
        }
    }

    fun getSeries(): LiveData<List<TvSeries>> {
        return Transformations.map(database.getSeries()) {
            it.asDomainModel()
        }
    }

}