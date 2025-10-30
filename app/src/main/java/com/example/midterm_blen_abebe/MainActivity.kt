package com.example.midterm_blen_abebe

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etNumber: EditText
    private lateinit var btnGenerate: Button
    private lateinit var btnHistory: Button
    private lateinit var lvTable: ListView

    private lateinit var adapter: ArrayAdapter<String>
    private val tableList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNumber = findViewById(R.id.etNumber)
        btnGenerate = findViewById(R.id.btnGenerate)
        btnHistory = findViewById(R.id.btnHistory)
        lvTable = findViewById(R.id.lvTable)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            tableList
        )
        lvTable.adapter = adapter

        btnGenerate.setOnClickListener {
            generateTable()
        }

        btnHistory.setOnClickListener {
            val i = Intent(this, HistoryActivity::class.java)
            startActivity(i)
        }

        lvTable.setOnItemClickListener { _, _, position, _ ->
            showDeleteDialog(position)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                AlertDialog.Builder(this)
                    .setTitle("Clear all?")
                    .setMessage("Delete all rows?")
                    .setPositiveButton("Yes") { _, _ ->
                        tableList.clear()
                        adapter.notifyDataSetChanged()
                        Toast.makeText(this, "All rows cleared", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun generateTable() {
        val text = etNumber.text.toString()
        if (text.isEmpty()) {
            Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show()
            return
        }

        val n = text.toInt()
        tableList.clear()
        for (i in 1..10) {
            tableList.add("$n × $i = ${n * i}")
        }
        adapter.notifyDataSetChanged()

        if (!DataStore.historyNumbers.contains(n)) {
            DataStore.historyNumbers.add(n)
        }
    }

    private fun showDeleteDialog(position: Int) {
        val item = tableList[position]
        AlertDialog.Builder(this)
            .setTitle("Delete row")
            .setMessage("Do you want to delete:\n$item ?")
            .setPositiveButton("Yes") { _, _ ->
                tableList.removeAt(position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Deleted: $item", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }
}
