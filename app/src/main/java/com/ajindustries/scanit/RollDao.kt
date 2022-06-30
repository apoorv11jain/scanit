package com.ajindustries.scanit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface RollDao {
    @Insert
    fun insertBook(rollEntity: RollEntity)

    @Query("SELECT * FROM rolls")
    fun getAllRolls() :List<RollEntity>

    @Query("SELECT * FROM rolls WHERE enrollno = :enrollno")
    fun getRollById(enrollno: String):RollEntity
}