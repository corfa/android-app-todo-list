package com.example.android_app.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_app.MyApplication
import com.example.android_app.R
import com.example.android_app.data.Item
import com.example.android_app.network.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BlankFragment @Inject constructor() : Fragment() {



    @Inject
    lateinit var myViewModel: MyViewModel

    private lateinit var recyclerView: RecyclerView

    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("jwt_token", "").toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addButton = view.findViewById<ImageView>(R.id.addButton)
        val btnExit = view.findViewById<ImageView>(R.id.btnExit)
        val inputLayout = view.findViewById<LinearLayout>(R.id.inputLayout)
        val inputField = view.findViewById<EditText>(R.id.inputField)
        val addButtonInLayout = view.findViewById<Button>(R.id.addButtonInLayout)
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = myViewModel.adapter
        myViewModel.token=token
        myViewModel.adapter.setItemClickListener(object : ItemClickListener {



            override fun onItemCheckboxClicked(item: Item) {
                myViewModel.removeItem(item)
            }




        })


        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        myViewModel.updateData()


        addButton.setOnClickListener {

            inputLayout.visibility = View.VISIBLE
            inputField.visibility = View.VISIBLE
            addButtonInLayout.visibility = View.VISIBLE
            inputField.requestFocus()
            addButton.visibility = View.GONE
        }

        btnExit.setOnClickListener {
            val sharedPreferences = requireActivity().applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("jwt_token")
            editor.apply()

            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.container, SingInFragment())
            transaction.remove(this@BlankFragment)
            transaction.commit()
        }




        addButtonInLayout.setOnClickListener {
            val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            val newDoing= inputField.text.toString()
            if (newDoing.isEmpty()){
                showToast(token)
                inputField.visibility = View.GONE
                addButtonInLayout.visibility = View.GONE
                inputLayout.visibility = View.VISIBLE
                addButton.visibility = View.VISIBLE
                inputField.text.clear()
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
            else {

                myViewModel.addItem(newDoing)
                inputField.visibility = View.GONE
                addButtonInLayout.visibility = View.GONE
                inputLayout.visibility = View.VISIBLE
                addButton.visibility = View.VISIBLE
                inputField.text.clear()
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)


            }
        }



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
    }



    private fun showToast(message: String) {
        Toast.makeText(requireContext(), "Message: $message", Toast.LENGTH_SHORT).show()
    }







}