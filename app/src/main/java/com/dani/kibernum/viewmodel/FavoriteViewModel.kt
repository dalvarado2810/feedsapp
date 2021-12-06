package com.dani.kibernum.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dani.kibernum.R
import com.dani.kibernum.data.db.AppDao
import com.dani.kibernum.data.db.AppDatabase
import com.dani.kibernum.data.db.FavDao
import com.dani.kibernum.data.model.*
import com.dani.kibernum.data.repository.FavoriteRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository?
    ): ViewModel() {


    fun getAllFavouritesList(): LiveData<AppResource<List<FavouriteFeeds>>>{
        val liveDataFav = MutableLiveData<AppResource<List<FavouriteFeeds>>>()
        viewModelScope.launch(Dispatchers.IO) {
            liveDataFav.postValue(favoriteRepository?.getFavourites())
        }
        return liveDataFav
    }

    fun markAsFavourite(favourite: FavoriteDto): LiveData<AppResource<FavoriteResponse?>> {
        val liveData = MutableLiveData<AppResource<FavoriteResponse?>>()
        viewModelScope.launch(Dispatchers.IO) {
            liveData.postValue(AppResource.Loading())
            liveData.postValue(favoriteRepository?.markAsFavorite(favourite))
        }
        return liveData
    }


    fun insertFavs(favourite: FavouriteFeeds) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository?.insertFavourite(favourite)
        }
    }

    fun deleteFavs(favouriteItem : FavouriteFeeds){
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository?.deleteFavourite(favouriteItem)
        }

    }



}