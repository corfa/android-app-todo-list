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

class SignUpViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiManager: ApiManager

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    fun createUser(username: String, password: String,sharedPreferences: SharedPreferences) {
        apiManager.createUser(username, password, object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    val token = response.body()
                    val jwtToken = token?.token

                    val editor = sharedPreferences.edit()
                    editor.putString("jwt_token", jwtToken)
                    editor.apply()

                    _messageLiveData.postValue("User created successfully")
                } else {
                    _messageLiveData.postValue("Username already used!")
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                _messageLiveData.postValue("Connection error!")
            }
        })
    }
}
