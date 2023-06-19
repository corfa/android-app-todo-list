package com.example.android_app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.android_app.data.TokenValid
import com.example.android_app.models.MainViewModel
import com.example.android_app.network.ApiManager
import com.example.android_app.ui.BlankFragment
import com.example.android_app.ui.SingInFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var blankFragment: BlankFragment
    @Inject
    lateinit var singInFragment: SingInFragment
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApplication).appComponent.inject(this)

        val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val jwtToken = sharedPreferences.getString("jwt_token", "")

        if (jwtToken.isNullOrBlank()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, singInFragment)
                .commit()
        } else {
            viewModel.checkToken(jwtToken)
            viewModel.tokenValid.observe(this, Observer<TokenValid> { tokenValid ->
                if (tokenValid != null) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, blankFragment)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, singInFragment)
                        .commit()
                }
            })
        }
    }
}







