package com.example.android_app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_app.data.TokenValid
import com.example.android_app.network.ApiManager
import com.example.android_app.ui.BlankFragment
import com.example.android_app.ui.SingInFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiManager: ApiManager
    @Inject
    lateinit var blankFragment: BlankFragment
    @Inject
    lateinit var singInFragment: SingInFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApplication).appComponent.inject(this)

        val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val jwtToken = sharedPreferences.getString("jwt_token", "")

        if (jwtToken.isNullOrEmpty()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, singInFragment)
                .commit()
        } else {

            apiManager.checkToken(jwtToken, object : Callback<TokenValid> {
                override fun onResponse(call: Call<TokenValid>, response: Response<TokenValid>) {
                    if (response.isSuccessful) {
                        supportFragmentManager.beginTransaction()
                            .add(R.id.container, blankFragment)
                            .commit()

                    } else {
                        supportFragmentManager.beginTransaction()
                            .add(R.id.container, singInFragment)
                            .commit()
                    }
                }

                override fun onFailure(call: Call<TokenValid>, t: Throwable) {
                }
            })

//            val btnExit = findViewById<ImageView>(R.id.btnExit)
//
//
//
//            btnExit.setOnClickListener {
//                val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//                val editor = sharedPreferences.edit()
//                editor.remove("jwt_token")
//                editor.apply()
//                supportFragmentManager.beginTransaction()
//                    .add(R.id.container, singInFragment)
//                    .commit()
//
//            }
        }


    }
}







