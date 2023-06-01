package com.example.accountspayable.Room.Data

data class DataItem(

    var id: Int,
    var name : String,
    var valor : Float,
    var descricao : String,
    var icon : String,
    var person1 : String,
    var check1 : Boolean,
    var person2 : String,
    var check2 : Boolean,
    var person3 : String,
    var check3 : Boolean,
    var person4 : String,
    var check4 : Boolean,
    var mYear: MonthYear

)

data class MonthYear(

    var month: Int,
    var year: Int


)
