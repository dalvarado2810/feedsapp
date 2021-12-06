package com.dani.kibernum.data.repository.source

import com.dani.kibernum.data.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RemoteApiSource {

    @POST("api/login")
    fun doLogin(@Body login: LoginDTO): Call<LoginResponse>

    @POST("api/favorite")
    fun markAsFavorite(@Header("apikey") apikey: String, @Body favorite: FavoriteDto): Call<FavoriteResponse>

    @GET("api/feed")
    fun getApiFeeds(@Header("apikey") apiKey: String): Call<List<FeedsItem>>

    @GET("api/contacts")
    fun getApiContacts () : Call<List<ContactsItem>>

}