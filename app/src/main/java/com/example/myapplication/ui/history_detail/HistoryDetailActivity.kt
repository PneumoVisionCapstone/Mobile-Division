package com.example.myapplication.ui.history_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHistoryDetailBinding

class HistoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryDetailBinding

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Get the data from the intent
//        val name = intent.getStringExtra(EXTRA_NAME)
//        val age = intent.getIntExtra(EXTRA_AGE, 0)
//        val gender = intent.getStringExtra(EXTRA_GENDER)
//        val imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
//        val result = intent.getStringExtra(EXTRA_RESULT)
//
//        // Set the data to the views
//        binding.edNamapasien.text = name
//        binding.edAge.text = age.toString()
//        binding.gender.text = gender
//        binding.tvResultHasil.text = result
//
//        // Load the image using Glide
//        Glide.with(this)
//            .load(imageUrl)
//            .placeholder(R.drawable.baseline_image_24)
//            .into(binding.previewparu)
//
//        // Set back button click listener
//        binding.btnBack.setOnClickListener {
//            finish()
//        }
//    }
//
//    companion object {
//        const val EXTRA_NAME = "extra_name"
//        const val EXTRA_AGE = "extra_age"
//        const val EXTRA_GENDER = "extra_gender"
//        const val EXTRA_IMAGE_URL = "extra_image_url"
//        const val EXTRA_RESULT = "extra_result"
//    }
}
