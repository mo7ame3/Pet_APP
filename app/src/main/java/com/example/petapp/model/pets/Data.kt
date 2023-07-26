package com.example.petapp.model.pets

import com.example.petapp.model.home.Data

data class Data(
    val RowCtor: Any,
    val _parsers: List<Any>,
    val _types: Types,
    val command: String,
    val fields: List<Field>,
    val oid: Any,
    val rowAsArray: Boolean,
    val rowCount: Int,
    val rows: List<Data>
)