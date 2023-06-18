package com.example.android_app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android_app.MainActivity
import com.example.android_app.MyApplication
import com.example.android_app.R
import com.example.android_app.data.Token
import com.example.android_app.network.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SingUpFragment @Inject constructor() : Fragment() {

    private lateinit var editTextCreateUsername: EditText
    private lateinit var editTexCreatePassword: EditText
    private lateinit var buttonCreate: Button

    @Inject
    lateinit var apiManager: ApiManager
    @Inject
    lateinit var blankFragment: BlankFragment



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sing_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextCreateUsername = view.findViewById(R.id.editTextCreateUsername)
        editTexCreatePassword = view.findViewById(R.id.editTexCreatePassword)
        buttonCreate = view.findViewById(R.id.buttonCreate)

        (requireActivity().application as MyApplication).appComponent.inject(this)

        buttonCreate.setOnClickListener {
            val username = editTextCreateUsername.text.toString()
            val password = editTexCreatePassword.text.toString()

            apiManager.createUser(username, password, object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful) {
                        val token = response.body()
                        val jwt_token = token?.token
                        val sharedPreferences = requireContext().applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("jwt_token", jwt_token)
                        editor.apply()
                        val fragmentManager = requireActivity().supportFragmentManager
                        val transaction = fragmentManager.beginTransaction()
                        transaction.replace(R.id.container, blankFragment)
                        transaction.commit()

                    } else {
                        showToast("username already used!")
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    showToast("connect error!")
                }
            })
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), "Message: $message", Toast.LENGTH_SHORT).show()
    }


}
