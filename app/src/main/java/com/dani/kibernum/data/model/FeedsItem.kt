package com.dani.kibernum.data.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.io.Serializable


@Entity(tableName = "feeds",
        foreignKeys = arrayOf(
        ForeignKey(
        entity = ContactsItem::class,
        parentColumns = arrayOf("id_author"),
        childColumns = arrayOf("author"),
        onDelete = CASCADE)))

data class FeedsItem(
    @PrimaryKey(autoGenerate = false)@ColumnInfo(name = "id_post")val id: Int?,
    @ColumnInfo(name = "title")val title: String?,
    @ColumnInfo(name = "description")val description: String?,
    @ColumnInfo(name = "image")val image: String?,
    @ColumnInfo(name = "date")val date: String?,
    @ColumnInfo(name = "author")val author_id: String?,
    @ColumnInfo(name = "link")val link: String?,
    @ColumnInfo(name = "published")val published: String?,
) : Serializable



