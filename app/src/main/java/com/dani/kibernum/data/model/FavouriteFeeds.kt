package com.dani.kibernum.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favourites")
data class FavouriteFeeds (
    @PrimaryKey(autoGenerate = false)@ColumnInfo(name = "id")val id: Int?,
    @ColumnInfo(name = "title")val title: String?,
    @ColumnInfo(name = "description")val description: String?,
    @ColumnInfo(name = "image")val image: String?,
    @ColumnInfo(name = "date")val date: String?,
    @ColumnInfo(name = "author")val author_id: String?,
    @ColumnInfo(name = "firstName")val firstName: String?,
    @ColumnInfo(name = "lastName")val lastName: String?
) : Serializable
