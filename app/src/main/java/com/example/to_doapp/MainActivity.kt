package com.example.to_doapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_doapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TaskDatabaseHelper
    private lateinit var taskAdapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= TaskDatabaseHelper(this)
        taskAdapter= TaskAdapter(db.getAllTasks(),this)


        binding.taskRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.taskRecyclerView.adapter= taskAdapter


        binding.AddBtn.setOnClickListener{
            val intent= Intent(this,AddNoteActivity::class.java)
            startActivity(intent)
        }




    }
    //to refresh the data
    override fun onResume() {
        super.onResume()
        taskAdapter.refreshData(db.getAllTasks())
    }
}