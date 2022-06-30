package com.ajindustries.scanit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rolls")
data class RollEntity (
        @PrimaryKey val enrollno: String =""
)