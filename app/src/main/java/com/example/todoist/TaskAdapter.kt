package com.example.todoist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private var tasks: List<Task>, private val context: Context) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val db: TaskDatabaseHelper = TaskDatabaseHelper(context)
    private var filteredTasks: List<Task> = tasks.toList()

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateBtn: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteBtn: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredTasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = filteredTasks[position]
        holder.titleTextView.text = task.title
        holder.contentTextView.text = task.content

        holder.updateBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateTaskActivity::class.java).apply {
                putExtra("task_id", task.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteBtn.setOnClickListener {
            db.deleteTask(task.id)
            refreshData(db.getAllTasks())
            Toast.makeText(holder.itemView.context, "Task Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun filterTasks(query: String) {
        filteredTasks = tasks.filter { task ->
            task.title.contains(query, ignoreCase = true) || task.content.contains(query, ignoreCase = true)
        }
        notifyDataSetChanged()
    }

    fun refreshData(newTasks: List<Task>) {
        tasks = newTasks
        filteredTasks = tasks.toList()
        notifyDataSetChanged()
    }
}