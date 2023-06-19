package com.example.android_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_app.data.TokenValid
import com.example.android_app.network.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel(){
    private val _tokenValid = MutableLiveData<TokenValid>()
    val tokenValid: LiveData<TokenValid> = _tokenValid


    @Inject
    lateinit var apiManager: ApiManager

    fun checkToken(jwtToken: String?) {
        if (jwtToken != null) {
            apiManager.checkToken(jwtToken, object : Callback<TokenValid> {
                override fun onResponse(call: Call<TokenValid>, response: Response<TokenValid>) {
                    if (response.isSuccessful) {
                        _tokenValid.value = response.body()
                    } else {
                        _tokenValid.value = null
                    }
                }

                override fun onFailure(call: Call<TokenValid>, t: Throwable) {
                    _tokenValid.value = null
                }
            })
        }
    }



}