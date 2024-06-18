package com.example.myapplication.ui.history

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.ViewModelFactory
import com.example.myapplication.databinding.ActivityHistoryBinding
import com.example.myapplication.retrofit.ApiConfig
import com.example.myapplication.response.HistoryResponseItem
import com.example.myapplication.ui.adapter.ScanHistoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: ScanHistoryAdapter
    private val viewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvLungs.layoutManager = LinearLayoutManager(this)

        viewModel.getSession().observe(this) { userModel ->
            token = userModel.token
            fetchScanHistory()
        }
    }

    private fun fetchScanHistory() {
        val token = this.token ?: return
        val client = ApiConfig.getApiService().getScanHistory("Bearer $token")
        client.enqueue(object : Callback<List<HistoryResponseItem>> {
            override fun onResponse(call: Call<List<HistoryResponseItem>>, response: Response<List<HistoryResponseItem>>) {
                if (response.isSuccessful) {
                    val scanHistoryList = response.body() ?: emptyList()
                    adapter = ScanHistoryAdapter(scanHistoryList)
                    binding.rvLungs.adapter = adapter
                } else {
                    Toast.makeText(this@HistoryActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<HistoryResponseItem>>, t: Throwable) {
                Log.e("HistoryActivity", "onFailure: ${t.message}")
                Toast.makeText(this@HistoryActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
