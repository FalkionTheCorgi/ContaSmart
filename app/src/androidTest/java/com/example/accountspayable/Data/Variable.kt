package com.example.accountspayable.Data

import androidx.compose.runtime.mutableStateListOf
import com.example.accountspayable.R
import com.example.accountspayable.Room.Item.ItemEntity
import com.example.accountspayable.Room.Summary.SummaryEntity
import com.example.accountspayable.getTodayDate

val ITEM_ENTITY_LESS_3DAYS_1 = ItemEntity(
    itemName = "Item1",
    price = 33.0,
    description = "desc1",
    icon = "Light",
    person1 = "Pessoa1",
    checkedPerson1 = false,
    person2 = "",
    checkedPerson2 = false,
    person3 = "Pessoa3",
    checkedPerson3 = false,
    person4 = "",
    checkedPerson4 = false,
    priceOfPerson = 11.0,
    vencimento = 4,
    month = getTodayDate()?.monthValue ?: -1,
    year = getTodayDate()?.year ?: 2023
)

val ITEM_ENTITY_LESS_3DAYS_2 = ItemEntity(
    itemName = "Item2",
    price = 60.0,
    description = "desc2",
    icon = "Water",
    person1 = "",
    checkedPerson1 = false,
    person2 = "Pessoa2",
    checkedPerson2 = false,
    person3 = "",
    checkedPerson3 = false,
    person4 = "Pessoa4",
    checkedPerson4 = false,
    priceOfPerson = 20.0,
    vencimento = 23,
    month = getTodayDate()?.monthValue ?: -1,
    year = getTodayDate()?.year ?: 2023
)

val ITEM_ENTITY_LESS_3DAYS_1_AFTER_EDIT = ItemEntity(
    itemName = "Item1",
    price = 33.0,
    description = "desc1",
    icon = "Light",
    person1 = "Teste1",
    checkedPerson1 = false,
    person2 = "",
    checkedPerson2 = false,
    person3 = "Teste3",
    checkedPerson3 = false,
    person4 = "",
    checkedPerson4 = false,
    priceOfPerson = 11.0,
    vencimento = 4,
    month = getTodayDate()?.monthValue ?: -1,
    year = getTodayDate()?.year ?: 2023
)

val ITEM_ENTITY_LESS_3DAYS_2_AFTER_EDIT = ItemEntity(
    itemName = "Item2",
    price = 60.0,
    description = "desc2",
    icon = "Water",
    person1 = "",
    checkedPerson1 = false,
    person2 = "Teste2",
    checkedPerson2 = false,
    person3 = "",
    checkedPerson3 = false,
    person4 = "Teste4",
    checkedPerson4 = false,
    priceOfPerson = 20.0,
    vencimento = 23,
    month = getTodayDate()?.monthValue ?: -1,
    year = getTodayDate()?.year ?: 2023
)

val ITEM_ENTITY_EDIT = ItemEntity(
    itemName = "Item Edit",
    price = 25.0,
    description = "description edit",
    icon = "Router",
    person1 = "",
    checkedPerson1 = false,
    person2 = "",
    checkedPerson2 = false,
    person3 = "",
    checkedPerson3 = false,
    person4 = "Pessoa4",
    checkedPerson4 = false,
    priceOfPerson = 12.50,
    vencimento = 22,
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

val listItems = listOf(
    ITEM_ENTITY_LESS_3DAYS_1,
    ITEM_ENTITY_LESS_3DAYS_2
)

val listItemsAfterEdit = listOf(
    ITEM_ENTITY_LESS_3DAYS_1_AFTER_EDIT,
    ITEM_ENTITY_LESS_3DAYS_2_AFTER_EDIT
)



val years = listOf(
    "2023",
    "2024",
    "2025",
    "2026",
    "2027",
    "2028",
    "2029",
    "2030",
    "2031",
    "2032",
    "2033"
)

val itemsDropDown = listOf("Light", "Water", "Market", "Router", "Card", "Restaurant", "Phone", "House", "Game", "Other")

val ITEM_NAME = "Item Novo Adicionado"
val ITEM_VALUE = "25.67"
val ITEM_DEADLINE_CORRECT = "25"
val ITEM_DEADLINE_ERROR = "76"
val ITEM_DESCRIPTION = "Descrição do item novo adicionado"

val SUMMARY_REVENUE = "1056.57"
val SUMMARY_PERSON1 = "Pessoa1"
val SUMMARY_PERSON2 = "Pessoa2"
val SUMMARY_PERSON3 = "Pessoa3"
val SUMMARY_PERSON4 = "Pessoa4"

val SUMMARY_REVENUE_EDIT = "701.57"
val SUMMARY_PERSON1_EDIT = "Teste1"
val SUMMARY_PERSON2_EDIT= "Teste2"
val SUMMARY_PERSON3_EDIT = "Teste3"
val SUMMARY_PERSON4_EDIT = "Teste4"