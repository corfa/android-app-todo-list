package com.example.android_app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_app.R
import com.example.android_app.data.Item
import javax.inject.Inject

interface ItemClickListener {
    fun onItemCheckboxClicked(item: Item)
}

class MyListAdapter  @Inject constructor() : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    private var dataList: List<Item> = emptyList()
    private var listener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    fun addItem(item: Item) {
        val updatedList = dataList.toMutableList()
        updatedList.add(item)
        dataList = updatedList
        notifyItemInserted(dataList.size - 1)
    }


    fun removeItem(item: Item) {
        val position = dataList.indexOf(item)
        if (position != -1) {
            dataList.toMutableList().apply {
                removeAt(position)
                dataList = this
            }
            notifyDataSetChanged()
        }
    }

    fun getPositionOfItem(item: Item): Int {
        return dataList.indexOf(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newData: List<Item>) {
        dataList = newData
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textDesc: TextView = itemView.findViewById(R.id.textDesc)
        private val checkboxDone: CheckBox = itemView.findViewById(R.id.checkboxDone)

        init {
            checkboxDone.setOnCheckedChangeListener { _, isChecked ->
                val clickedItem = dataList[adapterPosition]
                listener?.onItemCheckboxClicked(clickedItem)
            }
        }

        fun bind(item: Item) {
            textDesc.text = item.desc
            checkboxDone.isChecked = item.done
        }
    }
}