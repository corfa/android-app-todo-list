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
import android.widget.TextView
import android.widget.Toast
import com.example.android_app.MainActivity
import com.example.android_app.MyApplication
import com.example.android_app.R
import com.example.android_app.data.Token
import com.example.android_app.models.SignUpViewModel
import com.example.android_app.network.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SingUpFragment @Inject constructor() : Fragment() {

    private lateinit var editTextCreateUsername: EditText
    private lateinit var editTexCreatePassword: EditText
    private lateinit var buttonCreate: Button
    private lateinit var textViewAlreadyHaveAccount: TextView



    @Inject
    lateinit var blankFragment: BlankFragment

    @Inject
    lateinit var viewModel: SignUpViewModel



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
        textViewAlreadyHaveAccount = view.findViewById(R.id.textViewAlreadyHaveAccount)

        (requireActivity().application as MyApplication).appComponent.inject(this)

        buttonCreate.setOnClickListener {
            val username = editTextCreateUsername.text.toString()
            val password = editTexCreatePassword.text.toString()
            val sharedPreferences = requireContext().applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

            viewModel.createUser(username, password, sharedPreferences) { success ->
                if (success) {
                    val fragmentManager = requireActivity().supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.container, blankFragment)
                    transaction.commit()
                } else {
                    showToast("username already in use")
                }
            }
        }
        textViewAlreadyHaveAccount.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.container, SingInFragment())
            transaction.commit()
        }

        viewModel.messageLiveData.observe(viewLifecycleOwner) { message ->
            showToast(message)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), "Message: $message", Toast.LENGTH_SHORT).show()
    }
}

