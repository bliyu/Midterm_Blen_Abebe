package com.example.midterm_blen_abebe

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var lvHistory: ListView
    private lateinit var btnBack: Button
    private lateinit var adapter: ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        lvHistory = findViewById(R.id.lvHistory)
        btnBack = findViewById(R.id.btnBack)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            DataStore.historyNumbers
        )
        lvHistory.adapter = adapter

        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}
