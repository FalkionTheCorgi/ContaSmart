package com.example.accountspayable.Room.Summary

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "summary")
data class SummaryEntity  (

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val revenue : Float,
    val month: Int,
    val year: Int,
    val person1 : String,
    val person2 : String,
    val person3 : String,
    val person4 : String

)