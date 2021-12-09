package com.dani.kibernum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dani.kibernum.data.model.ContactsItem
import com.dani.kibernum.data.model.FavouriteFeeds
import com.dani.kibernum.data.model.FeedsItem
import com.dani.kibernum.data.model.relations.ContactsAndFeeds
import com.dani.kibernum.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: HomeRepository
    ): ViewModel() {

    fun getAllRepositoryList(): LiveData<AppResource<List<FeedsItem>>>{
        val liveData = MutableLiveData<AppResource<List<FeedsItem>>>()
        viewModelScope.launch(Dispatchers.IO) {
            liveData.postValue(AppResource.Loading())
            liveData.postValue(repository.getAllFeeds())

        }
        return liveData
    }

    fun getAllFavouritesList(): LiveData<AppResource<List<FavouriteFeeds>>>{
        val liveDataFav = MutableLiveData<AppResource<List<FavouriteFeeds>>>()
        viewModelScope.launch(Dispatchers.IO) {
            liveDataFav.postValue(repository?.getFavourites())
        }
        return liveDataFav
    }

    fun getAllContactsList(): LiveData<AppResource<List<ContactsItem>>>{
        val liveDataC = MutableLiveData<AppResource<List<ContactsItem>>>()
        viewModelScope.launch(Dispatchers.IO) {
            liveDataC.postValue(AppResource.Loading())
            liveDataC.postValue(repository.getAllContacts())
        }
        return liveDataC

    }

    fun getContactsAndFeeds(author : String): List<ContactsAndFeeds> {
        var liveDataCF : List<ContactsAndFeeds> = repository?.getContactsAndFeeds(author)

        return liveDataCF
    }



    }





