package com.dani.kibernum.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "contacts")
data class ContactsItem (
        @PrimaryKey (autoGenerate = false) @ColumnInfo (name = "id_author")val id : Int?,
        @ColumnInfo (name = "firstName") val firstName : String?,
        @ColumnInfo (name = "lastName")val lastName : String?,
        @ColumnInfo (name = "gender")val gender : String?
        ): Serializable