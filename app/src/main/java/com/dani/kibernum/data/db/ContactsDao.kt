package com.dani.kibernum.data.db

import androidx.room.*
import com.dani.kibernum.data.model.ContactsItem
import com.dani.kibernum.data.model.FeedsItem
import com.dani.kibernum.data.model.relations.ContactsAndFeeds
import com.dani.kibernum.viewmodel.AppResource

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<ContactsItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(contactsItem: ContactsItem)

    @Query("DELETE FROM contacts")
    fun deleteAllContacts()

    @Transaction
    @Query("SELECT * FROM contacts WHERE id_author = :author")
    fun getContactsAndFeeds(author : String): List<ContactsAndFeeds>

}