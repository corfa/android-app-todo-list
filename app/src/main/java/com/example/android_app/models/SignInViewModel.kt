package com.example.android_app.models

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_app.data.Token
import com.example.android_app.network.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class SignInViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiManager: ApiManager
    private var token: String = ""

    fun authenticateUser(username: String, password: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        apiManager.authUser(username, password, object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    token = response.body()?.token ?: ""

                    resultLiveData.postValue(true)
                } else {
                    resultLiveData.postValue(false)
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                resultLiveData.postValue(false)
            }
        })

        return resultLiveData
    }

    fun saveToken(sharedPreferences: SharedPreferences, token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("jwt_token", token)
        editor.apply()
    }

    fun getToken(): String {
        return token
    }
}
