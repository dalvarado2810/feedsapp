package com.dani.kibernum.data.model.relations

import android.provider.ContactsContract
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.dani.kibernum.data.model.ContactsItem
import com.dani.kibernum.data.model.FeedsItem
import java.io.Serializable

data class ContactsAndFeeds (
    @Embedded val contacts: ContactsItem,
    @Relation(
        parentColumn = "id_author",
        entityColumn = "author"
    )
    val feeds : List<FeedsItem>
        ):Serializable