package com.example.pamproject.dao

import androidx.room.*
import com.example.pamproject.entity.Currency

@Dao
interface CurrencyDAO {
    @Query("select * from currency")
    fun getAll(): List<Currency>

    @Query("select * from currency where id = :currId")
    fun getById(currId: Long): Currency

    @Query("select * from currency where code = :code")
    fun getByCode(code: String): Currency

    @Insert
    fun insertAll(vararg currencies: Currency)

    @Delete
    fun delete(currency: Currency)

    @Update
    fun update(currency: Currency)
}