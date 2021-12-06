package com.dani.kibernum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dani.kibernum.data.model.LoginDTO
import com.dani.kibernum.data.model.LoginResponse
import com.dani.kibernum.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginRepository: LoginRepository):
    ViewModel() {

    fun doLogin(login: LoginDTO): LiveData<AppResource<LoginResponse>> {
        val liveData = MutableLiveData<AppResource<LoginResponse>>()
        viewModelScope.launch(Dispatchers.IO) {
            liveData.postValue(AppResource.Loading())
            liveData.postValue(loginRepository.doLogin(login))
        }
        return liveData
    }

}