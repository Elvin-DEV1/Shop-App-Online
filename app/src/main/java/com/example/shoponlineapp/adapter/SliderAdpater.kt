package com.example.shoponlineapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions
import com.example.shoponlineapp.databinding.SliderItemContainerBinding
import com.example.shoponlineapp.model.SliderModel

class SliderAdpater(
    private var sliderItems: List<SliderModel>
) : RecyclerView.Adapter<SliderAdpater.SliderViewHolder>() {
    private lateinit var context: Context


    class SliderViewHolder(private val binding: SliderItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setImage(sliderModel: SliderModel, context: Context) {
            Glide.with(context)
                .load(sliderModel.url)
                .apply(RequestOptions().transform(CenterInside()))
                .into(binding.imageSlide)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
        context = parent.context
        val binding = SliderItemContainerBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position], context)
    }

    override fun getItemCount(): Int = sliderItems.size
}