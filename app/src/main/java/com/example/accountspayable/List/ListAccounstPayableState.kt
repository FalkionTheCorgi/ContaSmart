package com.example.accountspayable.List

import com.example.accountspayable.Room.Item.ItemEntity
import kotlinx.coroutines.flow.MutableStateFlow

class ListAccounstPayableState {

    val dataItem: MutableStateFlow<List<ItemEntity>> = MutableStateFlow(emptyList())

}