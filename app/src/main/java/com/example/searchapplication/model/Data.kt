package com.example.searchapplication.model

import java.io.Serializable

data class Data(
    var title: String? = "",
    var description: String? = "",
    var price: Int? = 0
) : Serializable