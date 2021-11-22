package com.example.lykkehjultest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val data: List<Words>): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    val items: List<Words>

    init {
        this.items = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {

        /*val dataFromMemory = Memory().loadWords()
        hemmeligtOrd = dataFromMemory.random().toString()*/

        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ViewHolder(adapterLayout)

    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.tvItem.text = data[position].text

    }

    override fun getItemCount(): Int {
        return data.size
        //return titles.size
    }

    fun getList():  List<Words>{
        return data
        //return titles.size
    }



    inner class ViewHolder
    internal constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvItem: TextView = itemView.findViewById(R.id.tvItem)
    }






}
