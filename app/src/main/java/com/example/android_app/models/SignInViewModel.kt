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
    // объявите необходимые данные для авторизации
    @Inject
    lateinit var apiManager: ApiManager
    private var token: String = "" // переменная для хранения токена

    // метод для выполнения авторизации
    fun authenticateUser(username: String, password: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        // выполните логику авторизации через apiManager
        apiManager.authUser(username, password, object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    // успешная авторизация
                    token = response.body()?.token ?: ""
                    // сохраните токен или другие данные, если необходимо

                    resultLiveData.postValue(true) // уведомите о успешной авторизации
                } else {
                    // ошибка авторизации
                    resultLiveData.postValue(false) // уведомите об ошибке авторизации
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                // ошибка при выполнении запроса
                resultLiveData.postValue(false) // уведомите об ошибке при выполнении запроса
            }
        })

        return resultLiveData
    }

    fun saveToken(sharedPreferences: SharedPreferences, token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("jwt_token", token)
        editor.apply()
    }

    // метод для получения токена
    fun getToken(): String {
        return token
    }
}
