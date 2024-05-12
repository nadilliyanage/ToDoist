package com.example.todoist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todoist.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddTaskBinding
    private lateinit var db: TaskDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= TaskDatabaseHelper(this)


//finish method like intent to the main activity
        binding.saveBtn.setOnClickListener{
            val title = binding.titleedittext.text.toString()
            val content = binding.contentEditText.text.toString()
            val task = Task(0,title,content)
            db.insertTask(task)
            finish()
            Toast.makeText(this,"Task Saved",Toast.LENGTH_SHORT).show()
        }
    }
}