package com.dani.kibernum.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dani.kibernum.data.model.ContactsItem
import com.dani.kibernum.data.model.FavouriteFeeds
import com.dani.kibernum.data.model.FeedsItem

@Dao
interface FavDao {

    @Query("SELECT * FROM favourites")
    fun getAllFavourites(): List<FavouriteFeeds>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourites(favouritesItem: FavouriteFeeds)

    @Delete
    suspend fun deleteFavourite(favouritesItem: FavouriteFeeds)

    @Query("DELETE FROM favourites")
    suspend fun removeFavourite()


}