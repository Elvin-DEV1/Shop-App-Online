package com.example.shoponlineapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.shoponlineapp.R
import com.example.shoponlineapp.adapter.CategoryAdapter
import com.example.shoponlineapp.adapter.PopularAdapter
import com.example.shoponlineapp.adapter.SliderAdpater
import com.example.shoponlineapp.databinding.ActivityMainBinding
import com.example.shoponlineapp.model.SliderModel
import com.example.shoponlineapp.viewModel.MainViewModel
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initBanner()
        initCategory()
        initRecommended()
        initBottomMenu()

        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
        }
    }

    private fun initBottomMenu() {
        binding.CartBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }

    private fun initRecommended() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.popular.observe(this, Observer {
            binding.viewPopular.layoutManager =
                GridLayoutManager(this@MainActivity, 2)
            binding.viewPopular.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.category.observe(this, Observer {
            binding.viewCategory.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewCategory.adapter = CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE
        })
        viewModel.loadCategory()
    }

    private fun initBanner() {
        binding.progressBarSlider.visibility = View.VISIBLE
        viewModel.banner.observe(this, Observer {
            banners(it)
            binding.progressBarSlider.visibility = View.GONE
        })
        viewModel.loadBanner()
    }

    private fun banners(image: List<SliderModel>) {
        binding.viewPager2.adapter = SliderAdpater(image)
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewPager2.setPageTransformer(compositePageTransformer)

        if (image.size > 1) {
            binding.dotindicator.visibility = View.VISIBLE
            binding.dotindicator.attachTo(binding.viewPager2)
        }
    }
}