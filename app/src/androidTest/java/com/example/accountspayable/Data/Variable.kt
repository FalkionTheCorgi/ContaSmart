package com.example.accountspayable.Data

import com.example.accountspayable.Room.Item.ItemEntity
import com.example.accountspayable.Room.Summary.SummaryEntity
import com.example.accountspayable.getTodayDate

val ITEM_ENTITY_LESS_3DAYS = ItemEntity(
    itemName = "Item1",
    price = 33.0,
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
    priceOfPerson = 33.0,
    vencimento = 4,
    month = getTodayDate()?.monthValue ?: -1,
    year = getTodayDate()?.year ?: 2023
)

val SUMMARY_CREATED_TODAY = SummaryEntity(
    revenue = 332.0f,
    month = getTodayDate()?.monthValue ?: 1,
    year = getTodayDate()?.year ?: 2023,
    person1 = "Pessoa1",
    person2 = "Pessoa2",
    person3 = "Pessoa3",
    person4 = "Pessoa4"
)

val SUMMARY_CREATED_OTHER_MONTH = SummaryEntity(
    revenue = 417.0f,
    month = 1,
    year =  2023,
    person1 = "Pessoa1",
    person2 = "Pessoa2",
    person3 = "Pessoa3",
    person4 = "Pessoa4"
)