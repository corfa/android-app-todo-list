package com.example.android_app.ui

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.example.android_app.R
import com.example.android_app.data.Item
import com.example.android_app.network.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class BlankViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var adapter: MyListAdapter
    @Inject
    lateinit var apiManager: ApiManager

    public  lateinit var token: String

    private val isAddInProgress = AtomicBoolean(false)

    fun addItem(newDoing: String) {
        if (!isAddInProgress.getAndSet(true)) {
            apiManager.createDoing(newDoing, token, object : Callback<Item?> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<Item?>, response: Response<Item?>) {
                    if (response.isSuccessful) {
                        val item = response.body()
                        if (item != null) {
                            adapter.addItem(item)
                            adapter.notifyItemInserted(adapter.itemCount - 1)
                        } else {
                            // Handle the error
                        }
                    } else {
                        // Handle the error
                    }

                    val delayMillis = 700L
                    Handler(Looper.getMainLooper()).postDelayed({
                        isAddInProgress.set(false)
                    }, delayMillis)
                }

                override fun onFailure(call: Call<Item?>, t: Throwable) {
                    // Handle the failure
                    isAddInProgress.set(false)
                }
            })
        }
    }

    private val isDeleteInProgress = AtomicBoolean(false)

    fun removeItem(item: Item) {
        if (!isDeleteInProgress.getAndSet(true)) {
            item.id?.let { id ->
                apiManager.deleteDoing(id, token, object : Callback<Item?> {
                    override fun onResponse(call: Call<Item?>, response: Response<Item?>) {
                        if (response.isSuccessful) {
                            val position = adapter.getPositionOfItem(item)
                            adapter.removeItem(item)
                            adapter.notifyItemRemoved(position)
                        } else {
                            // Handle the error
                        }

                        val delayMillis = 700L
                        Handler(Looper.getMainLooper()).postDelayed({
                            isDeleteInProgress.set(false)
                        }, delayMillis)
                    }

                    override fun onFailure(call: Call<Item?>, t: Throwable) {
                        isDeleteInProgress.set(false)
                    }
                })
            }
        }
    }



    fun updateData(): Boolean {
        var isSuccess = false
        apiManager.getTasks(token, object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    val itemList = response.body()
                    val dataList = itemList ?: emptyList()
                    adapter.updateData(dataList)
                    isSuccess = true
                } else {
                    isSuccess = false
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                isSuccess = false
            }
        })
        return isSuccess
    }




}
