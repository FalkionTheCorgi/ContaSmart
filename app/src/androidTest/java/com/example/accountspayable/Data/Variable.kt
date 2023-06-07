package com.example.accountspayable.Data

import com.example.accountspayable.Room.Item.ItemEntity
import com.example.accountspayable.getTodayDate

val ITEM_ENTITY_LESS_3DAYS = ItemEntity(
    itemName = "Item1",
    price = 33f,
    description = "desc1",
    icon = "Light",
    person1 = "",
    checkedPerson1 = false,
    person2 = "",
    checkedPerson2 = false,
    person3 = "",
    checkedPerson3 = false,
    person4 = "",
    checkedPerson4 = false,
    priceOfPerson = 33f,
    vencimento = 4,
    month = getTodayDate()?.monthValue ?: -1,
    year = getTodayDate()?.year ?: 2023
)