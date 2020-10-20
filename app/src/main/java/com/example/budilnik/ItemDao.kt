package com.example.budilnik

import androidx.room.*

@Dao
interface ItemDao {

    @Insert
    fun insert(item: Item)

    @Query("SELECT * FROM items")
    fun getAll(): List<Item>

    @Query("UPDATE items SET isOn=:isOn WHERE id=:id")
    fun toggleOnOff(id: Int, isOn: Boolean = true)

    @Delete
    fun delete(item: Item)

}