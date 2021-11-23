package com.dani.kibernum.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "feeds")
data class FeedsItem(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "fd")val fd: Int = 0,
    @ColumnInfo(name = "title")val title: String?,
    @ColumnInfo(name = "description")val description: String?,
    @ColumnInfo(name = "image")val image: String?,
    @ColumnInfo(name = "author")val author_id: String?,
    @ColumnInfo(name = "date")val date: String?,
    @ColumnInfo(name = "id")val id: Int?,
    @ColumnInfo(name = "link")val link: String?,
    @ColumnInfo(name = "published")val published: String?
) : Serializable



