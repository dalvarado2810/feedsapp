package com.dani.kibernum.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dani.kibernum.data.model.ContactsItem
import com.dani.kibernum.data.model.FavouriteFeeds
import com.dani.kibernum.data.model.FeedsItem
import com.dani.kibernum.data.model.TypeConverterOwner


@Database(entities = [FeedsItem::class, ContactsItem::class, FavouriteFeeds::class],
    version = 1,
    exportSchema = false)
@TypeConverters(TypeConverterOwner::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao
    abstract fun getContactDao(): ContactsDao
    abstract fun getFavDao(): FavDao


companion object {
    @Volatile
    private var DB_INSTANCE: AppDatabase? = null

    fun getAppDBInstance(context: Context): AppDatabase {
        if(DB_INSTANCE == null) {
            DB_INSTANCE =  Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "APP_DB")
                .allowMainThreadQueries()
                .build()
        }
        return DB_INSTANCE!!
    }
}
}