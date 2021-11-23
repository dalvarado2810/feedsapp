package com.dani.kibernum.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dani.kibernum.data.model.FeedsItem



@Dao
interface AppDao {

    @Query("SELECT * FROM feeds")
    fun getAllRecords(): List<FeedsItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(feedsItem: FeedsItem)

    @Query("DELETE FROM feeds")
    fun deleteAllRecords()



}