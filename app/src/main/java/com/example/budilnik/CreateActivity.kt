package com.example.budilnik

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        setupViews()
    }

    private fun setupViews() {
        btn_add.setOnClickListener {

            val newItem = Item(
                title = item_title.text.toString(),
                time = item_time.hour.toString().padStart(2, '0') + ":" + item_time.minute.toString().padStart(2, '0')
            )

            AsyncTask.execute {
                AppDatabase.getInstance(applicationContext)!!
                    .getItemsDao().insert(newItem)

                runOnUiThread {
                    finish()
                }
            }
        }
    }

}
