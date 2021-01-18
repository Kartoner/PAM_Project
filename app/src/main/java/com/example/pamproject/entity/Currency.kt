package com.example.pamproject.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency (
    @ColumnInfo(name = "code")
    var code: String?,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "value")
    var value: Double?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0
}