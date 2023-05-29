package com.example.mytask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    val taskList: ArrayList<TaskModel>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    interface OnTaskClickListener {
        fun onTaskClick(taskAdapter : TaskAdapter, view : View, task : TaskModel, position : Int)
    }

    var onTaskClickListener : OnTaskClickListener? = null

     inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtAddTitle: TextView
        val editTextDescription: TextView

        init {
            txtAddTitle = view.findViewById(R.id.edtAddtitle)
            editTextDescription = view.findViewById(R.id.editTextDescription)

            view.setOnClickListener {
                onTaskClickListener?.onTaskClick(
                    this@TaskAdapter,
                    view,
                    taskList[adapterPosition],
                    adapterPosition
                )
            }
        }

    }

    override fun getItemCount() = taskList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_task_view, parent,false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val contact = taskList[position]

        holder.txtAddTitle.setText(contact.title)
        holder.editTextDescription.setText(contact.description)
    }
}