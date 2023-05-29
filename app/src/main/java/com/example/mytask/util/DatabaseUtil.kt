package com.example.mytask.util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mytask.TaskModel

class DatabaseUtil(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {


        private const val DATABASE_NAME = "task_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun addTask(title: String, description: String): Long {
        val values = ContentValues()
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_DESCRIPTION, description)

        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
    }

    fun updateTask(id: Long, title: String, description: String): Int {
        val values = ContentValues()
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_DESCRIPTION, description)

        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())

        return db.update(TABLE_NAME, values, selection, selectionArgs)
    }

    @SuppressLint("Range")
    fun readTasks(): List<TaskModel> {
        val tasks = mutableListOf<TaskModel>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor!!.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))

            val task = TaskModel( title, description)
            tasks.add(task)
        }

        cursor.close()
        return tasks
    }

    fun deleteTask(id: Long): Int {
        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())

        return db.delete(TABLE_NAME, selection, selectionArgs)
    }
}
