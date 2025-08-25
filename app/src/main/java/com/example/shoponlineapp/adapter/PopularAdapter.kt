package com.example.shoponlineapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoponlineapp.activity.DetailActivity
import com.example.shoponlineapp.databinding.ViewholderPopularBinding
import com.example.shoponlineapp.model.ItemsModel

class PopularAdapter(private val items: List<ItemsModel>) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderPopularBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            titleTxt.text = item.title
            priceTxt.text = "$${item.price}"
            ratingTxt.text = item.rating.toString()

            Glide.with(holder.itemView.context)
                .load(item.picUrl[0])
                .into(pic)

            root.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                    putExtra("object", item)
                }
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int = items.size

}