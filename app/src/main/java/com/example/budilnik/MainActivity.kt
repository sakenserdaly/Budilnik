package com.example.budilnik

import android.app.AlertDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val itemsAdapter by lazy {
        ItemsAdapter(
            onSwitchClick = { item ->
                toggleItem(item)
            },
            onItemClick = { item ->
                showLeftTimeAlert(item)
            },
            onItemLongClick = { item ->
                showDeleteDialog(item)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    private fun setupViews() {
        items_view.layoutManager = LinearLayoutManager(this)
        items_view.adapter = itemsAdapter

        loadData()

        btn_add.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)

            startActivity(intent)
        }
    }

    private fun loadData() {

        AsyncTask.execute {

            val alarms = AppDatabase.getInstance(applicationContext)!!
                .getItemsDao().getAll()

            runOnUiThread {
                itemsAdapter.addItems(alarms)
            }
        }
    }

    private fun deleteItem(item: Item) {
        AsyncTask.execute {

            AppDatabase.getInstance(applicationContext)!!
                .getItemsDao().delete(item)

        }

        loadData()
    }

    private fun toggleItem(item: Item) {
        AsyncTask.execute {

            AppDatabase.getInstance(applicationContext)!!
                .getItemsDao().toggleOnOff(item.id!!, !item.isOn)

        }

        loadData()
    }

    private fun showLeftTimeAlert(item: Item) {

        val c = Calendar.getInstance()

        val alarmTime = item.time.split(':')
        val alarmHour = alarmTime[0].toInt()
        val alarmMinute = alarmTime[1].toInt()

        val currentHour = c.get(Calendar.HOUR_OF_DAY)
        val currentMinute = c.get(Calendar.MINUTE)

        var leftTime = (alarmHour * 60 +  alarmMinute) - (currentHour * 60 + currentMinute)

        if(leftTime < 0) {
            leftTime += 1440
        }

        val leftHour = leftTime / 60
        val leftMinute = leftTime % 60

        val builder = AlertDialog.Builder(this)

        builder.setTitle("$leftHour hours $leftMinute minutes left")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
        }

        builder.show()

    }

    private fun showDeleteDialog(item: Item) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Do you want to delete?")

        builder.setPositiveButton("Yes") { dialog, which ->
            deleteItem(item)
        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        builder.show()
    }
}
