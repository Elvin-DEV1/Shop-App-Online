package com.example.shoponlineapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoponlineapp.R
import com.example.shoponlineapp.databinding.ViewholderPicBinding

class PicAdapter(private val items: List<String>, private val onImageSelected: (String) -> Unit) :
    RecyclerView.Adapter<PicAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    private var lastSelectionPosition = RecyclerView.NO_POSITION

    class ViewHolder(val binding: ViewholderPicBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderPicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.pic.loadImage(item)

        if (selectedPosition == position) {
            holder.binding.picLayout.setBackgroundResource(R.drawable.blue_bg_selected)
        } else {
            holder.binding.picLayout.setBackgroundResource(0)
        }

        holder.binding.root.setOnClickListener {
            val position = position
            if (position != RecyclerView.NO_POSITION) {
                lastSelectionPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(lastSelectionPosition)
                notifyItemChanged(selectedPosition)

                onImageSelected(item)
            }

        }
    }

    private fun ImageView.loadImage(url: String){
        Glide.with(this.context).load(url).into(this)
    }
}