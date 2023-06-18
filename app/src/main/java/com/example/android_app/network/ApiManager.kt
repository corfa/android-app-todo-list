package com.example.android_app.network

import com.example.android_app.data.Credentials
import com.example.android_app.data.Doing
import com.example.android_app.data.Item
import com.example.android_app.data.Token
import com.example.android_app.data.TokenValid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Inject

class ApiManager @Inject constructor(private val apiService: ApiService) {

    fun authUser(username: String, password: String, callback: Callback<Token>) {
        val credentials = Credentials(username, password)
        apiService.authUser(credentials).enqueue(callback)
    }

    fun getTasks(token: String, callback: Callback<List<Item>>) {
        apiService.getTasks(token).enqueue(callback)
    }

    fun createDoing(desc: String, token: String, callback: Callback<Item?>) {
        val doing = Doing(desc)
        apiService.createDoing(token, doing).enqueue(callback)
    }

    fun deleteDoing(id: String, token: String, callback: Callback<Item?>) {
        apiService.deleteDoing(token, id).enqueue(callback)
    }

    fun createUser(username: String, password: String, callback: Callback<Token>) {
        val credentials = Credentials(username, password)
        apiService.createUser(credentials).enqueue(callback)
    }
    fun checkToken(token:String,callback: Callback<TokenValid>){
        val tokenJwt = Token(token)
        apiService.checkToken(tokenJwt).enqueue(callback)
    }
}


