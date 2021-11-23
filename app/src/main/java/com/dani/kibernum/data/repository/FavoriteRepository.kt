package com.dani.kibernum.data.repository

import com.dani.kibernum.data.model.FavoriteDto
import com.dani.kibernum.data.repository.source.MyPreference
import com.dani.kibernum.data.repository.source.RemoteApiSource
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val remoteApiSource: RemoteApiSource,
    private val sharedPreferences: MyPreference
) {
    fun markAsFavorite(favoriteDto: FavoriteDto) {

        remoteApiSource.markAsFavorite(sharedPreferences.getStoredAuthToken(), favoriteDto)

    }
}