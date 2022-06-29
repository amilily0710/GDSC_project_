package com.example.gdsc_project.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gdsc_project.R
import com.example.gdsc_project.model.Policy
import com.example.gdsc_project.model.User

class NewsAdapter(private val dataset:ArrayList<Policy>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.text_item)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return NewsViewHolder(layout)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.toString()

    }


    override fun getItemCount(): Int {
        return dataset.size
    }
}