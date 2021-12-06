package com.dani.kibernum.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dani.kibernum.data.db.FavDao
import com.dani.kibernum.data.model.*
import com.dani.kibernum.data.repository.source.MyPreference
import com.dani.kibernum.data.repository.source.RemoteApiSource
import com.dani.kibernum.viewmodel.AppResource
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val remoteApiSource: RemoteApiSource,
    private val sharedPreferences: MyPreference,
    private val favDao : FavDao
) {


    fun markAsFavorite(favoriteDto: FavoriteDto) : AppResource<FavoriteResponse?> {

        try {
            val response = remoteApiSource.markAsFavorite(
                sharedPreferences.getStoredAuthToken(),
                favoriteDto).
                execute()
            return if (response.isSuccessful) {
                manageFavSuccessResponse(response.body())
            } else {
                when (response.code()) {
                    400 -> AppResource.Error("Invalid Credentials")
                    else -> AppResource.Error("Network Error")
                }
            }
        } catch (e: Exception) {
            return AppResource.Error("Network Error")
        }

    }

    private fun manageFavSuccessResponse(body: FavoriteResponse?): AppResource<FavoriteResponse?> {

        return if(body?.status == "OK") {
            //sharedPreferences.setStoredToken(body?.apiToket)
            AppResource.Success(body)

        } else {
            AppResource.Error("Not allowed to save favourite")
        }
    }

    suspend fun deleteFavourite(favouriteItem : FavouriteFeeds){
        favDao.deleteFavourite(favouriteItem)
    }

    suspend fun insertFavourite(favouriteItem: FavouriteFeeds){
        favDao.insertFavourites(favouriteItem)
    }

    fun getFavourites(): AppResource<List<FavouriteFeeds>> {

        val favourites = favDao.getAllFavourites()

        return if (favourites.isNotEmpty()) {
            AppResource.Success(favourites)
        } else {
            AppResource.Error(message = "There´s no Favourites")
        }
    }

}