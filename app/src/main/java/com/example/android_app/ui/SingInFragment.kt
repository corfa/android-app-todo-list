package com.example.android_app.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.android_app.MyApplication
import com.example.android_app.R
import com.example.android_app.data.Token
import com.example.android_app.network.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class SingInFragment @Inject constructor(): Fragment() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var textViewWelcome: TextView
    private lateinit var textViewCreateAccount: TextView
    private lateinit var buttonLogin: Button

    @Inject
    lateinit var apiManager: ApiManager

    @Inject
    lateinit var singUpFragment: SingUpFragment

    @Inject
    lateinit var blankFragment: BlankFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_sing_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as MyApplication).appComponent.inject(this)

        editTextUsername = view.findViewById(R.id.editTextUsername)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        textViewWelcome = view.findViewById(R.id.textViewWelcome)
        textViewCreateAccount = view.findViewById(R.id.textViewCreateAccount)
        buttonLogin = view.findViewById(R.id.buttonLogin)




        textViewCreateAccount.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.container, singUpFragment)
            transaction.commit()

        }

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            apiManager.authUser(username, password, object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful) {
                        val token = response.body()?.token

                        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()

                        editor.putString("jwt_token", token)
                        editor.apply()

                        val newFragment = BlankFragment()
                        val fragmentManager = requireActivity().supportFragmentManager
                        val transaction = fragmentManager.beginTransaction()
                        transaction.replace(R.id.container, newFragment)
                        transaction.commit()
                    } else {
                        Toast.makeText(requireContext(), "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Toast.makeText(requireContext(), "Ошибка при выполнении запроса ${t.toString()}", Toast.LENGTH_SHORT).show()
                }
            })
        }





    }

}

