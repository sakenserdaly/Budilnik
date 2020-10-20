package com.example.budilnik

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "items"
)

data class Item(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "time") var time: String,
    @ColumnInfo(name = "isOn") var isOn: Boolean = true
)