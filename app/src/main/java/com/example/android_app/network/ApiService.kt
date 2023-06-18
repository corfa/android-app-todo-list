package com.example.android_app.network

import com.example.android_app.data.Credentials
import com.example.android_app.data.Doing
import com.example.android_app.data.Item
import com.example.android_app.data.Token
import com.example.android_app.data.TokenValid
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path



interface ApiService {
    @POST("user/auth")
    fun authUser(@Body credentials: Credentials): Call<Token>

    @GET("doing/")
    fun getTasks(@Header("X-Token") token: String): Call<List<Item>>

    @POST("doing/")
    fun createDoing(@Header("X-Token") token: String, @Body doing: Doing): Call<Item>

    @POST("user/")
    fun createUser( @Body user: Credentials): Call<Token>

    @DELETE("doing/{id}")
    fun deleteDoing(@Header("X-Token") token: String, @Path("id") doingId: String): Call<Item>

    @POST("token/check")
    fun checkToken( @Body token: Token): Call<TokenValid>
}