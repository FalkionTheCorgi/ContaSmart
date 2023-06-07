package com.example.accountspayable.Room.Item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemName : String,
    val price : Double,
    val description : String,
    val icon : String,
    val person1: String,
    val checkedPerson1: Boolean,
    val person2: String,
    val checkedPerson2: Boolean,
    val person3: String,
    val checkedPerson3: Boolean,
    val person4: String,
    val checkedPerson4: Boolean,
    val priceOfPerson: Double,
    val vencimento: Int,
    val month : Int,
    val year : Int


)