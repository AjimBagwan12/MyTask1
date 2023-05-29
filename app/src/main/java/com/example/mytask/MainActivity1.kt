package com.example.mytask

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.util.DatabaseUtil

class MainActivity : AppCompatActivity() {
    private lateinit var rvMylist:RecyclerView
    private var protyList = ArrayList<String>()
    private lateinit var addtitle : EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonAdd: Button
    private lateinit var listViewItems: ListView

    private lateinit var todoListAdapter: ArrayAdapter<String>
    private val todoItems = mutableListOf<TaskModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSpinner()
        rvMylist =findViewById(R.id.rvMylist)
        addtitle = findViewById(R.id.edtAddtitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        buttonAdd = findViewById(R.id.buttonAdd)

        // listViewItems = findViewById(R.id.listViewItems)


        buttonAdd.setOnClickListener {
            val dbUtil = DatabaseUtil(this@MainActivity)
            val taskId = dbUtil.addTask(addtitle.text.toString() , editTextDescription.text.toString())

            readData()
        }
    }

    private fun readData(){
        val dbUtil = DatabaseUtil(this@MainActivity)
        val tasks = dbUtil.readTasks()

        rvMylist.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
        rvMylist.adapter = TaskAdapter(tasks as ArrayList<TaskModel>)
        for (i in tasks){
            Log.e("MyTask","${i.title} + ${i.description}")
        }
    }

    private fun setSpinner() {

        protyList.add("Low")
        protyList.add("Mid")
        protyList.add("High")

        val spinner = findViewById<Spinner>(R.id.myspinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, protyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


    }


//    private fun addTodoItem1(title: String){
//        val item1 = title
//        todoItems.add(item1)
//        todoListAdapter.notifyDataSetChanged()
//    }

    private fun addTodoItem(title: String,description: String) {
        val item = description
        todoItems.add(TaskModel(title,description))
        todoListAdapter.notifyDataSetChanged()
    }

}




//class MainActivity : Activity()