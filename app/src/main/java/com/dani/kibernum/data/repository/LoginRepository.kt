package com.dani.kibernum.data.repository

import com.dani.kibernum.data.model.LoginDTO
import com.dani.kibernum.data.model.LoginResponse
import com.dani.kibernum.data.repository.source.MyPreference
import com.dani.kibernum.data.repository.source.RemoteApiSource
import com.dani.kibernum.viewmodel.AppResource
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val remoteApiSource: RemoteApiSource,
    private val sharedPreferences: MyPreference
) {

    fun doLogin(login: LoginDTO): AppResource<LoginResponse> {

        try {
            val response = remoteApiSource.doLogin(login = login).execute()
            return if (response.isSuccessful) {
                manageSuccessResponse(response.body())
            } else {
                when (response.code()) {
                    400 -> AppResource.Error("Bad username or password")
                    else -> AppResource.Error("Network Error")
                }
            }
        } catch (e: Exception) {
            return AppResource.Error("Network Error")
        }

    }


    private fun manageSuccessResponse(body: LoginResponse?): AppResource<LoginResponse> {

        return if(body?.status == "OK") {
            sharedPreferences.setStoredToken(body?.apiToket)
            AppResource.Success(body)
        } else {
            AppResource.Error("Not allowed to login")
        }

    }



}