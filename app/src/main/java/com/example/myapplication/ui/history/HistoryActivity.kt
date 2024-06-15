package com.example.myapplication.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.pneumuvision.response.LungsResponse
import com.example.myapplication.R
import com.example.myapplication.ui.adapter.ListHistoryAdapter

class HistoryActivity : AppCompatActivity() {

    private lateinit var rvLungs: RecyclerView
    private val list = ArrayList<LungsResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        rvLungs = findViewById(R.id.rv_lungs)
        rvLungs.setHasFixedSize(true)

        list.addAll(getListAnimal())
        showRecyclerList()
    }

    private fun getListAnimal(): ArrayList<LungsResponse> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listAnimal = ArrayList<LungsResponse>()
        for (i in dataName.indices) {
            val animal = LungsResponse(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listAnimal.add(animal)
        }
        return listAnimal
    }

    private fun showRecyclerList() {
        rvLungs.layoutManager = LinearLayoutManager(this)
        val listAnimalAdapter = ListHistoryAdapter(list)
        rvLungs.adapter = listAnimalAdapter
    }
}
