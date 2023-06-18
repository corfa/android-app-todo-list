package com.example.android_app.models

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_app.data.Item
import com.example.android_app.network.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
//
//class BlankViewModel @Inject constructor(private val apiManager: ApiManager, private val sharedPreferences: SharedPreferences) : ViewModel() {
//
//    private val _tasksLiveData = MutableLiveData<List<Item>>()
//    val tasksLiveData: LiveData<List<Item>> = _tasksLiveData
//
//    private val _messageLiveData = MutableLiveData<String>()
//    val messageLiveData: LiveData<String> = _messageLiveData
//
//    private val _deleteInProgressLiveData = MutableLiveData<Boolean>()
//
//    private lateinit var token: String
//
//    fun init(token: String) {
//        this.token = token
//        getTasks()
//    }
//
//    fun getTasks() {
//        apiManager.getTasks(token, object : Callback<List<Item>> {
//            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
//                if (response.isSuccessful) {
//                    val itemList = response.body()
//                    val dataList = itemList ?: emptyList()
//                    _tasksLiveData.postValue(dataList)
//                } else {
//                    _messageLiveData.postValue("connect error!")
//                }
//            }
//
//            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
//                // Handle failure
//            }
//        })
//    }
//
//    fun createDoing(newDoing: String) {
//        apiManager.createDoing(newDoing, token, object : Callback<Item?> {
//            override fun onResponse(call: Call<Item?>, response: Response<Item?>) {
//                if (response.isSuccessful) {
//                    val item = response.body()
//                    if (item != null) {
//                        _tasksLiveData.value?.let { tasks ->
//                            val updatedList = tasks.toMutableList()
//                            updatedList.add(item)
//                            _tasksLiveData.postValue(updatedList)
//                        }
//                    } else {
//                        _messageLiveData.postValue("connect error!")
//                    }
//                } else {
//                    _messageLiveData.postValue("connect error!")
//                }
//            }
//
//            override fun onFailure(call: Call<Item?>, t: Throwable) {
//                // Handle failure
//            }
//        })
//    }
//
//    fun deleteDoing(item: Item) {
//        _deleteInProgressLiveData.postValue(true)
//
//        item.id?.let { itemId ->
//            apiManager.deleteDoing(itemId, token, object : Callback<Item?> {
//                override fun onResponse(call: Call<Item?>, response: Response<Item?>) {
//                    if (response.isSuccessful) {
//                        _tasksLiveData.value?.let { tasks ->
//                            val updatedList = tasks.toMutableList()
//                            val index = updatedList.indexOfFirst { it.id == itemId }
//                            if (index != -1) {
//                                updatedList.removeAt(index)
//                                _tasksLiveData.postValue(updatedList)
//                            }
//                        }
//                        _messageLiveData.postValue("Success")
//                    } else {
//                        _messageLiveData.postValue("Connect error!")
//                    }
//
//                    _deleteInProgressLiveData.postValue(false)
//                }
//
//                override fun onFailure(call: Call<Item?>, t: Throwable) {
//                    _deleteInProgressLiveData.postValue(false)
//                    _messageLiveData.postValue("Request failed!")
//                }
//            })
//        }
//    }
//
//    fun clearToken() {
//        val editor = sharedPreferences.edit()
//        editor.remove("jwt_token")
//        editor.apply()
//    }
//}
