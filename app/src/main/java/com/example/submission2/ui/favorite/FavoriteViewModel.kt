package com.example.submission2.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.database.Favorite
import com.example.submission2.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }
    fun update(favorite: Favorite) {
        mFavoriteRepository.update(favorite)
    }
    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }
}