package com.dani.kibernum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dani.kibernum.data.model.FeedsItem
import com.dani.kibernum.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: HomeRepository
    ): ViewModel() {

    fun getAllRepositoryList(): LiveData<AppResource<List<FeedsItem>>> {
        val liveData = MutableLiveData<AppResource<List<FeedsItem>>>()
        viewModelScope.launch(Dispatchers.IO) {
            liveData.postValue(AppResource.Loading())
            liveData.postValue(repository.getAllRecords())
        }
        return liveData
    }

}

