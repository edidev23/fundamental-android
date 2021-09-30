package com.example.submission2.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.database.Favorite
import com.example.submission2.model.UserDetailResponse
import com.example.submission2.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val _detailFavorite = MutableLiveData<Favorite>()
    val detailFavorite: LiveData<Favorite> = _detailFavorite

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()

    fun getDetail(username: String): LiveData<Favorite?> = mFavoriteRepository.getDetail(username)

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