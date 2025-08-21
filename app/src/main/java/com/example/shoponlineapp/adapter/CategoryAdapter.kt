package com.example.shoponlineapp.adapter

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoponlineapp.R
import com.example.shoponlineapp.activity.ListItemsActivity
import com.example.shoponlineapp.databinding.ViewholderCategoryBinding
import com.example.shoponlineapp.model.CategoryModel

class CategoryAdapter(private val items: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    private var lastSelectionPosition = RecyclerView.NO_POSITION

    class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleTxt.text = item.title

        if (selectedPosition == position) {
            holder.binding.titleTxt.setBackgroundResource(R.drawable.blue_bg)
            holder.binding.titleTxt.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
        } else {
            holder.binding.titleTxt.setBackgroundResource(R.drawable.light_blue_bg)
            holder.binding.titleTxt.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        }

        holder.binding.root.setOnClickListener {
            val position = position
            if (position != RecyclerView.NO_POSITION) {
                lastSelectionPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(lastSelectionPosition)
                notifyItemChanged(selectedPosition)
            }
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(holder.itemView.context, ListItemsActivity::class.java).apply {
                    putExtra("id", item.id.toString())
                    putExtra("title", item.title)

                }
                holder.itemView.context.startActivity(intent)
            }, 1000)
        }
    }
}