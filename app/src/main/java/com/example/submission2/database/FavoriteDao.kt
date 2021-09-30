package com.example.submission2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)
    @Update
    fun update(favorite: Favorite)
    @Delete
    fun delete(favorite: Favorite)
    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<Favorite>>
    @Query("SELECT * from favorite where login = :login")
    fun getDetail(login: String): LiveData<Favorite?>
}