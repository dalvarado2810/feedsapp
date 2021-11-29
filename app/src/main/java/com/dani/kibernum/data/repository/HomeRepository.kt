package com.dani.kibernum.data.repository

import com.dani.kibernum.data.db.AppDao
import com.dani.kibernum.data.db.ContactsDao
import com.dani.kibernum.data.model.ContactsItem
import com.dani.kibernum.data.model.FeedsItem
import com.dani.kibernum.data.model.relations.ContactsAndFeeds
import com.dani.kibernum.data.repository.source.MyPreference
import com.dani.kibernum.data.repository.source.RemoteApiSource
import com.dani.kibernum.viewmodel.AppResource
import retrofit2.Call
import java.lang.Exception
import javax.inject.Inject

class HomeRepository  @Inject constructor(
    private val apiFeeds: RemoteApiSource,
    private val appDao : AppDao,
    private val contactsDao: ContactsDao,
    private val sharedPreferences: MyPreference
) {
    fun getAllFeeds(): AppResource<List<FeedsItem>>{
        val call: Call<List<FeedsItem>> = apiFeeds.getApiFeeds(
            sharedPreferences.getStoredAuthToken())

        try {
            val response = call.execute()
            return if (response.isSuccessful) {
                val feedsList = response.body()
                appDao.deleteAllRecords()
                feedsList?.let {
                    it.forEach { feed ->
                        insertRecord(feed)
                    }
                    AppResource.Success(it)
                } ?: AppResource.Error("Error with the data income")
            } else {
                getFallbackData()
            }

        } catch (e: Exception) {
            return getFallbackData()
        }
    }

    fun getAllContacts(): AppResource<List<ContactsItem>>{
        val callC: Call<List<ContactsItem>> = apiFeeds.getApiContacts()
        try {
            val responseC = callC.execute()
            return if (responseC.isSuccessful) {

                val contactsList = responseC.body()
                contactsDao.deleteAllContacts()
                contactsList?.let {
                    it.forEach { Contact ->
                        insertContact(Contact)
                    }
                    AppResource.Success(it)

                } ?: AppResource.Error("Error with the data income")
            } else {
                getFallbackDataContact()
            }

        } catch (e: Exception) {
            return getFallbackDataContact()
        }
    }

    private fun getFallbackData(): AppResource<List<FeedsItem>> {

        val feeds = appDao.getAllRecords()

        return if (feeds.isNotEmpty()) {
            AppResource.Success(feeds)
        } else {
            AppResource.Error(message = "Network Error  X")
        }
    }

    private fun insertRecord(feedsItem: FeedsItem){
        appDao.insertRecord(feedsItem)
    }


    private fun insertContact(contactItem: ContactsItem) {
        contactsDao.insertContacts(contactItem)
    }


    private fun getFallbackDataContact(): AppResource<List<ContactsItem>> {
         val contacts = contactsDao.getAllContacts()
         return if (contacts.isNotEmpty()) {
                AppResource.Success(contacts)
         } else {
                AppResource.Error(message = "Network Error data")
         }
    }

}