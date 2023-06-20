package com.example.accountspayable.Room.Summary

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.accountspayable.Room.Item.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SummaryDao {

    @Query("SELECT * FROM summary")
    suspend fun getAllSummary(): List<SummaryEntity>

    @Query("SELECT * FROM summary WHERE id = :id")
    suspend fun getSummaryById(id: Int): SummaryEntity?

    @Query("SELECT * FROM summary WHERE month = :month AND year = :year")
    fun getASummaryByMonthAndYear(month: Int, year: Int): Flow<SummaryEntity?>

    @Insert
    suspend fun insertSummary(summary: SummaryEntity)

    @Delete
    suspend fun deleteSummary(summary: SummaryEntity)

    @Query("DELETE FROM summary WHERE month = :month AND year = :year")
    suspend fun deleteSummaryByMonthAndYear(month: Int, year: Int)

    @Query("UPDATE summary SET revenue = :revenue, person1 = :person1, person2 = :person2, person3 = :person3, person4 = :person4 WHERE id LIKE :id")
    suspend fun updateSummary(id: Int, revenue: Float, person1:String, person2: String, person3: String, person4: String)

    @Query("DELETE FROM summary WHERE id = :id")
    suspend fun deleteSummaryById(id: Int)

}