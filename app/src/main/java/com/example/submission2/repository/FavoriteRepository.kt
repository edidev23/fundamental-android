package com.example.submission2.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submission2.database.Favorite
import com.example.submission2.database.FavoriteDao
import com.example.submission2.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mDao.getAllFavorite()

    fun insert(favorite: Favorite) {
        executorService.execute { mDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mDao.delete(favorite) }
    }

    fun update(favorite: Favorite) {
        executorService.execute { mDao.update(favorite) }
    }
}