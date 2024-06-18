package com.example.myapplication.ui.detail_scan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.ui.history.HistoryActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val btnBack: ImageView = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        val previewParu: ImageView = findViewById(R.id.itemPhoto)
        val nameTextView: TextView = findViewById(R.id.tv_name)
        val ageTextView: TextView = findViewById(R.id.tv_age)
        val genderTextView: TextView = findViewById(R.id.tv_gender)
        val resultTextView: TextView = findViewById(R.id.tv_classification_result)

        val imageUri = intent.getStringExtra("EXTRA_IMAGE_URI")?.let { Uri.parse(it) }
        val name = intent.getStringExtra("EXTRA_NAME")
        val age = intent.getStringExtra("EXTRA_AGE")
        val gender = intent.getStringExtra("EXTRA_GENDER")
        val result = intent.getStringExtra("EXTRA_RESULT")

        imageUri?.let {
            previewParu.setImageURI(it)
        }
        nameTextView.text = name
        ageTextView.text = age
        genderTextView.text = gender
        resultTextView.text = result
    }
}
