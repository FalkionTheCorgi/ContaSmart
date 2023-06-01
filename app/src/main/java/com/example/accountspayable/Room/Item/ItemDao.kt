package com.example.accountspayable.Room.Item

import androidx.room.*

@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<ItemEntity>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemById(id: Int): ItemEntity?

    @Query("SELECT * FROM items WHERE itemName = :name")
    suspend fun getItemByName(name: String): ItemEntity?

    @Query("SELECT * FROM items WHERE month = :month AND year = :year")
    suspend fun getAllItemsByMonthAndYear(month: Int, year: Int): List<ItemEntity>

    @Query("SELECT * FROM items WHERE month = :month AND year = :year AND checkedPerson1 = :checkedPerson1 AND person1 = :person1")
    suspend fun getAllItemsByPersonChecked1(month: Int, year: Int, checkedPerson1: Boolean, person1: String): List<ItemEntity>

    @Query("SELECT * FROM items WHERE month = :month AND year = :year AND checkedPerson2 = :checkedPerson2 AND person2 = :person2")
    suspend fun getAllItemsByPersonChecked2(month: Int, year: Int, checkedPerson2: Boolean, person2: String): List<ItemEntity>

    @Query("SELECT * FROM items WHERE month = :month AND year = :year AND checkedPerson3 = :checkedPerson3 AND person3 = :person3")
    suspend fun getAllItemsByPersonChecked3(month: Int, year: Int, checkedPerson3: Boolean, person3: String): List<ItemEntity>

    @Query("SELECT * FROM items WHERE month = :month AND year = :year AND checkedPerson4 = :checkedPerson4 AND person4 = :person4")
    suspend fun getAllItemsByPersonChecked4(month: Int, year: Int, checkedPerson4: Boolean, person4: String): List<ItemEntity>

    @Insert
    suspend fun insertItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Query("DELETE FROM items WHERE month = :month AND year = :year")
    suspend fun deleteAllItems(month: Int, year: Int)

    @Query("UPDATE items SET itemName = :name, price = :price, description = :description, icon = :icon, person1 = :person1, person2 = :person2, person3 = :person3, person4 = :person4, priceOfPerson = :priceOfPerson , checkedPerson1 = :checkedPerson1, checkedPerson2 =:checkedPerson2, checkedPerson3 = :checkedPerson3, checkedPerson4 = :checkedPerson4 WHERE id LIKE :id")
    suspend fun updateItem(id: Int, name: String, price:Float, description: String, icon: String, person1: String, person2: String, person3: String, person4: String, priceOfPerson: Float, checkedPerson1: Boolean, checkedPerson2: Boolean, checkedPerson3: Boolean, checkedPerson4: Boolean)

    @Query("UPDATE items SET checkedPerson1 = :checkedPerson1, checkedPerson2 =:checkedPerson2, checkedPerson3 = :checkedPerson3, checkedPerson4 = :checkedPerson4 WHERE id LIKE :id")
    suspend fun updateCheckBoxPeople(id: Int, checkedPerson1: Boolean, checkedPerson2: Boolean, checkedPerson3: Boolean, checkedPerson4: Boolean)

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun deleteById(id: Int)

}