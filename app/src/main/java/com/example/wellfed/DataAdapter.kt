package com.example.wellfed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter (private val items: MutableList<DataModel>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private lateinit var myListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(itemView, myListener)
    }

    override fun onBindViewHolder(holder: DataAdapter.ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.postImage.setImageResource(currentItem.photo)
        holder.postTitle.text = currentItem.title
    }

//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        binding = ItemPostBinding.inflate(inflater, parent, false)
//        return ViewHolder(binding)
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
//        return ViewHolder(itemView)
//    }
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val currentItem = items[position]
//        holder.bind(items[position])
//    }
    override fun getItemCount() = items.size


    inner  class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val postImage : ImageView = itemView.findViewById(R.id.ImageFromCamera)
        val postTitle : TextView = itemView.findViewById(R.id.postTitle)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
//    inner class ViewHolder(itemView: ItemPostBinding) : RecyclerView.ViewHolder(itemView.root) {
//    fun bind(item : DataModel) {
//            binding.apply{
//                postTitle.text = item.title
//                image.setImageResource(item.photo)
//            }
//        }
//
//    }

}