package com.example.todoist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TaskDatabaseHelper
    private lateinit var taskAdapter: TaskAdapter
    private var allTasks: List<Task> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)
        allTasks = db.getAllTasks()

        taskAdapter = TaskAdapter(allTasks, this)

        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.taskRecyclerView.adapter = taskAdapter

        binding.AddBtn.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchTasks(it) }
                return true
            }
        })
    }

    private fun searchTasks(query: String) {
        val filteredTasks = db.getAllTasks(query)
        taskAdapter.refreshData(filteredTasks)
    }

    override fun onResume() {
        super.onResume()
        allTasks = db.getAllTasks()
        taskAdapter.refreshData(allTasks)
    }
}