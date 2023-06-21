package com.example.accountspayable.Room.Item

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemById(id: Int): ItemEntity?

    @Query("SELECT * FROM items WHERE itemName = :name")
    suspend fun getItemByName(name: String): ItemEntity?

    @Query("SELECT * FROM items WHERE month = :month AND year = :year")
    fun getAllItemsByMonthAndYear(month: Int, year: Int): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE vencimento >= :todayData AND vencimento <= :vencimento AND month = :month AND year = :year")
    suspend fun getAllItemsByDeadlineTomorrow(
        todayData: Int,
        vencimento: Int,
        month: Int,
        year: Int
    ): List<ItemEntity>

    @Query("SELECT * FROM items WHERE (month = :month AND year = :year) AND ((checkedPerson1 = :checkedPerson1 AND person1 = :person1) OR (checkedPerson2 = :checkedPerson2 AND person2 = :person2) OR (checkedPerson3 = :checkedPerson3 AND person3 = :person3) OR (checkedPerson4 = :checkedPerson4 AND person4 = :person4))")
    fun getAllItemsByPersonChecked(month: Int, year: Int, checkedPerson1: Boolean, person1: String, checkedPerson2: Boolean, person2: String, checkedPerson3: Boolean, person3: String, checkedPerson4: Boolean, person4: String): Flow<List<ItemEntity>>

    @Insert
    suspend fun insertItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Query("DELETE FROM items WHERE month = :month AND year = :year")
    suspend fun deleteAllItems(month: Int, year: Int)

    @Query("UPDATE items SET itemName = :name, price = :price, description = :description, icon = :icon, vencimento = :vencimento, person1 = :person1, person2 = :person2, person3 = :person3, person4 = :person4, priceOfPerson = :priceOfPerson , checkedPerson1 = :checkedPerson1, checkedPerson2 =:checkedPerson2, checkedPerson3 = :checkedPerson3, checkedPerson4 = :checkedPerson4 WHERE id LIKE :id")
    suspend fun updateItem(id: Int, name: String, price:Double, description: String, icon: String, vencimento: Int, person1: String, person2: String, person3: String, person4: String, priceOfPerson: Double, checkedPerson1: Boolean, checkedPerson2: Boolean, checkedPerson3: Boolean, checkedPerson4: Boolean)

    @Query("UPDATE items SET checkedPerson1 = :checkedPerson1, checkedPerson2 =:checkedPerson2, checkedPerson3 = :checkedPerson3, checkedPerson4 = :checkedPerson4 WHERE id LIKE :id")
    suspend fun updateCheckBoxPeople(id: Int, checkedPerson1: Boolean, checkedPerson2: Boolean, checkedPerson3: Boolean, checkedPerson4: Boolean)

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun deleteById(id: Int)

}