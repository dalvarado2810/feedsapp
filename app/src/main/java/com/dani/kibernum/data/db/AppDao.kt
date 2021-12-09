package com.dani.kibernum.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dani.kibernum.data.model.ContactsItem
import com.dani.kibernum.data.model.FeedsItem
import com.dani.kibernum.data.model.relations.ContactsAndFeeds


@Dao
interface AppDao {

    @Query("SELECT * FROM feeds")
    fun getAllRecords(): List<FeedsItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(feedsItem: FeedsItem)

    @Query("DELETE  FROM feeds")
    fun deleteAllRecords()


}